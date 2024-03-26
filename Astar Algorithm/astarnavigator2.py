'''
 * Copyright (c) 2014, 2015 Entertainment Intelligence Lab, Georgia Institute of Technology.
 * Originally developed by Mark Riedl.
 * Last edited by Mark Riedl 05/2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
'''

import sys, pygame, math, numpy, random, time, copy
from pygame.locals import *

from constants import *
from utils import *
from core import *


###############################
### AStarNavigator2
###
### Creates a path node network and implements the A* algorithm to create a path to the given destination.

class AStarNavigator2(PathNetworkNavigator):

    ### Finds the shortest path from the source to the destination using A*.
    ### self: the navigator object
    ### source: the place the agent is starting from (i.e., its current location)
    ### dest: the place the agent is told to go to
    def computePath(self, source, dest):
        self.setPath(None)
        ### Make sure the next and dist matrices exist
        if self.agent != None and self.world != None:
            self.source = source
            self.destination = dest
            ### Step 1: If the agent has a clear path from the source to dest, then go straight there.
            ### Determine if there are no obstacles between source and destination (hint: cast rays against world.getLines(), check for clearance).
            ### Tell the agent to move to dest
            if clearShot(source, dest, self.world.getLinesWithoutBorders(), self.world.getPoints(), self.agent):
                self.agent.moveToTarget(dest)
            else:
                ### Step 2: If there is an obstacle, create the path that will move around the obstacles.
                ### Find the path nodes closest to source and destination.
                start = getOnPathNetwork(source, self.pathnodes, self.world.getLinesWithoutBorders(), self.agent)
                end = getOnPathNetwork(dest, self.pathnodes, self.world.getLinesWithoutBorders(), self.agent)
                if start != None and end != None:
                    ### Remove edges from the path network that intersect gates
                    newnetwork = unobstructedNetwork(self.pathnetwork, self.world.getGates(), self.world)
                    closedlist = []
                    ### Create the path by traversing the pathnode network until the path node closest to the destination is reached
                    path, closedlist = astar(start, end, newnetwork)
                    if path is not None and len(path) > 0:
                        ### Determine whether shortcuts are available
                        path = shortcutPath(source, dest, path, self.world, self.agent)
                        ### Store the path by calling self.setPath()
                        self.setPath(path)
                        if self.path is not None and len(self.path) > 0:
                            ### Tell the agent to move to the first node in the path (and pop the first node off the path)
                            first = self.path.pop(0)
                            self.agent.moveToTarget(first)
        return None

    ### Called when the agent gets to a node in the path.
    ### self: the navigator object
    def checkpoint(self):
        myCheckpoint(self)
        return None

    ### This function gets called by the agent to figure out if some shortcuts can be taken when traversing the path.
    ### This function should update the path and return True if the path was updated.
    def smooth(self):
        return mySmooth(self)

    def update(self, delta):
        myUpdate(self, delta)


### Removes any edge in the path network that intersects a worldLine (which should include gates).
def unobstructedNetwork(network, worldLines, world):
    newnetwork = []
    for l in network:
        hit = rayTraceWorld(l[0], l[1], worldLines)
        if hit == None:
            newnetwork.append(l)
    return newnetwork


### Returns true if the agent can get from p1 to p2 directly without running into an obstacle.
### p1: the current location of the agent
### p2: the destination of the agent
### worldLines: all the lines in the world
### agent: the Agent object
def clearShot(p1, p2, worldLines, worldPoints, agent):
    ### YOUR CODE GOES BELOW HERE ###
    if p1 is None or p2 is None or worldLines is None or worldPoints is None:
        return False
    
    collision = rayTraceWorld(p1, p2, worldLines)
    if collision is not None:
        return False

    # Check each point in the world
    for point in worldPoints:
        # Calculate the minimum distance from the agent's path to the point
        minimum_distance = minimumDistance([p1, p2], point)

        # Check if the minimum distance is less than the agent's radius
        if minimum_distance < agent.getMaxRadius():
            return False

    # No collisions detected
    return True

    ### YOUR CODE GOES ABOVE HERE ###
    # return False


### Given a location, find the closest pathnode that the agent can get to without collision
### agent: the agent
### location: the location to check from (typically where the agent is starting from or where the agent wants to go to) as an (x, y) point
### pathnodes: a list of pathnodes, where each pathnode is an (x, y) point
### world: pointer to the world
def getOnPathNetwork(location, pathnodes, worldLines, agent):
    node = None
    ### YOUR CODE GOES BELOW HERE ###
    node = findClosestUnobstructed(location, pathnodes, worldLines)
    ### YOUR CODE GOES ABOVE HERE ###
    return node


### Implement the a-star algorithm
### Given:
### Init: a pathnode (x, y) that is part of the pathnode network
### goal: a pathnode (x, y) that is part of the pathnode network
### network: the pathnode network
### Return two values: 
### 1. the path, which is a list of states that are connected in the path network
### 2. the closed list, the list of pathnodes visited during the search process
def astar(init, goal, network):
    path = []
    open = []
    closed = []
    parent = {}  # Dictionary to store parent of each node

    # Check if the initial node is the goal node
    if init == goal:
        return [init], [init]

    open.append(init)  # Add Start Node To OPEN List

    # Create List of All Points Stored in Nodes[]
    nodes = list(set([node for point in network for node in point]))

    # Calculate G_Cost and H_Cost
    G_cost = [float('inf')] * len(nodes)
    H_cost = [distance(node, goal) for node in nodes]

    # Set G_cost for the initial node
    G_cost[nodes.index(init)] = 0

    while open:
        # Find the node in open list with the lowest F_cost
        open_costs = {node: G_cost[nodes.index(node)] + H_cost[nodes.index(node)] for node in open}
        current = min(open_costs, key=open_costs.get)

        # Check if the goal node is reached
        if current == goal:
            break

        # Mark the current node as visited
        open.remove(current)
        closed.append(current)

        # print("Expanded node:", current)

        # Finding Neighbors of Current Node
        neighbors = []
        for tup in network:
            if current in tup:
                neighbors.append(tup[1] if tup[0] == current else tup[0])

        # For each Neighbor of the Current Node
        for neighbor in neighbors:
            if neighbor not in closed:
                # Calculate tentative G_cost from the current node to the neighbor
                tentative_g_cost = G_cost[nodes.index(current)] + distance(current, neighbor)

                if tentative_g_cost < G_cost[nodes.index(neighbor)]:
                    # Update G_cost for this node
                    G_cost[nodes.index(neighbor)] = tentative_g_cost

                    # Update the parent node for this neighbor
                    parent[neighbor] = current

                    if neighbor not in open:
                        open.append(neighbor)  # Add the neighbor to the open list

    # Reconstruct the path
    current = goal
    while current != init:
        if current not in parent:
            return [], closed  # No path exists
        path.insert(0, current)  # Insert the current node at the beginning of the path
        current = parent[current]  # Move to the parent node

    # Add the start node to the path
    path.insert(0, init)

    return path, closed


def myUpdate(nav, delta):
    ### YOUR CODE GOES BELOW HERE ###

    ### YOUR CODE GOES ABOVE HERE ###
    return None


def myCheckpoint(nav):
    ### YOUR CODE GOES BELOW HERE ###

    ### YOUR CODE GOES ABOVE HERE ###
    return None

def isNodeReachable(source, target, worldLines, worldPoints, agent):
    # Check if there's a clear shot from source to target
    if clearShot(source, target, worldLines, worldPoints, agent):
        print("BOJOUR")
        return True

    # If not, check if there's an unobstructed path using ray tracing
    intersection = rayTraceWorldNoEndPoints(source, target, worldLines)
    print("HERE")
    if intersection is None:
        return True  # No obstacles found along the path
        print("HERE2")
    else:
        print("HERE3")
        return False  # Obstacle found, path is not reachable


### This function optimizes the given path and returns a new path
### source: the current position of the agent
### dest: the desired destination of the agent
### path: the path previously computed by the A* algorithm
### world: pointer to the world
def shortcutPath(source, dest, path, world, agent):
    worldLines = world.getLinesWithoutBorders()  # Get the lines from the world without borders
    worldPoints = world.getPoints()  # Get all the points from the world
    shortenedPath = [source]
    i = 0
    while i < len(path) - 1:
        for j in range(i + 1, len(path)):
            # Check if path[j] is not None before calling isNodeReachable
            if path[j] is not None and not isNodeReachable(shortenedPath[-1], path[j], worldLines, worldPoints, agent):
                # If path[j] is not reachable from the last point in the shortened path, add path[j-1] to the shortened path
                shortenedPath.append(path[j-1])
                i = j - 1
                break
        else:
            # If all remaining points are reachable from the last point in the shortened path, add the last point to the shortened path
            shortenedPath.append(path[-1])
            break
        i += 1
    if shortenedPath[-1] != dest:
        shortenedPath.append(dest)
    return shortenedPath





### This function changes the move target of the agent if there is an opportunity to walk a shorter path.
### This function should call nav.agent.moveToTarget() if an opportunity exists and may also need to modify nav.path.
### nav: the navigator object
### This function returns True if the moveTarget and/or path is modified and False otherwise
def mySmooth(navigator):
    if navigator.path is None:
        return False

    # Check if the agent can move directly to the destination
    if clearShot(navigator.agent.position, navigator.destination, navigator.world.getLines(), navigator.world.getPoints(), navigator.agent):
        navigator.path = []  # Clear the path
        navigator.agent.moveToTarget(navigator.destination)  # Move directly to the destination
        return True

    # Check each node in the path starting from the end
    for i in range(len(navigator.path) - 1, -1, -1):
        if clearShot(navigator.agent.position, navigator.path[i], navigator.world.getLines(), navigator.world.getPoints(), navigator.agent):
            navigator.path = navigator.path[:i+1]  # Remove all nodes after the current node
            navigator.agent.moveToTarget(navigator.path[i])  # Move to the current node
            return True

    # The agent cannot move directly to any node in the path
    return False


