# AStarNavigator2

One of the main uses of artificial intelligence in games is to perform **path planning**, the search for a sequence of movements through the virtual environment that gets an agent from one location to another without running into any obstacles. 

This script implements the A* algorithm to create a path to the given destination in python.


## License

The pygame script is based on code developed by the Entertainment Intelligence Lab, Georgia Institute of Technology, and originally developed by Mark Riedl. It is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

## Usage

1. **Setup**: Ensure you have the necessary dependencies installed, including Python, Pygame, and any required libraries.

2. **Integration**: Integrate this script into your Unity project to enable A* pathfinding for your agents.

3. **Configuration**: Customize the script as needed for your specific application, adjusting parameters such as obstacle clearance and node network generation.

## Features

- **A* Pathfinding**: Utilizes the A* algorithm to find the shortest path from the source to the destination.
- **Path Smoothing**: Implements path smoothing techniques to optimize the computed path.
- **License**: Licensed under the Apache License, Version 2.0, allowing for flexibility in usage and distribution.

## Dependencies

- **Python**: This script is written in Python and requires a Python environment to run.
- **Pygame**: Pygame library is used for handling game-related functionalities.
- **NumPy**: NumPy library is used for numerical computing.

## Code Overview

- **`computePath(source, dest)`**: Finds the shortest path from the source to the destination using the A* algorithm.
- **`checkpoint()`**: Called when the agent gets to a node in the path.
- **`smooth()`**: Optimizes the computed path by removing unnecessary nodes.
- **`update(delta)`**: Updates the navigator.