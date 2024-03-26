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

import sys, pygame, math, numpy, random, time, copy, operator
from pygame.locals import *

from constants import *
from utils import *
from core import *


# Creates the path network as a list of lines between all path nodes that are traversable by the agent.
def myBuildPathNetwork(pathnodes, world, agent=None):
    lines = []
    ### YOUR CODE GOES BELOW HERE ###
    """
    (1) Create a list that follows the following format:
    - [(p1, p2), (p1,p3), (p1,p4),...(p2, p1)...]
    - This will connect all pathnodes to every scenario
    """
    all_lines = []
    # obstacle_lines = world.getLinesWithoutBorders()
    for p1 in range(len(pathnodes)):
        for p2 in range(len(pathnodes)):
            if p1 != p2:
                all_lines.append((pathnodes[p1], pathnodes[p2]))
    """
    (2) Connect Path Nodes for the agent.
    - No obstacles between the 2 path nodes.
    - Use the rayTrace() to detect whether the lines intersect.
    - Got the obstacle lines via the 'world' parameter in the function 
    """
    obstacle_lines = world.getLinesWithoutBorders()
    is_collision = False
    no_obj_lines = []
    for p1 in range(len(all_lines)):
        for p2 in range(p1 + 1, len(all_lines)):
            point1 = all_lines[p1][0]
            point2 = all_lines[p2][1]
            for x in range(len(obstacle_lines)):
                collision = rayTrace(point1, point2, obstacle_lines[x])
                if collision is not None:
                    is_collision = True
            if not is_collision:
                no_obj_lines.append((point1, point2))
            is_collision = False
    """
    (3) Make Sufficient Space for Agent so he may move!
    - Agent Size (25.894)
    - Use minimumDistance() to detect the distance between the path network and the obstacle points
    """
    agent = agent.getMaxRadius()
    obstacle_points = world.getPoints()
    is_near = False
    # print(obstacle_points)
    # print(len(obstacle_points))
    # print(obstacle_points[1])
    for x in range(len(no_obj_lines)):
        for y in range(len(obstacle_points)):
            minimum_distance = minimumDistance(no_obj_lines[x], obstacle_points[y])
            if minimum_distance <= agent:  # half of the character width 1/2 for optimization!
                is_near = True
        if not is_near:
            lines.append(no_obj_lines[x])
        is_near = False
    ### YOUR CODE GOES ABOVE HERE ###
    return lines
