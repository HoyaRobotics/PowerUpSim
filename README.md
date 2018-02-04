# FIRST Power-Up Simulator
This program simulates scenarios for FIRST Power-Up games in an attempt to forumulate the best strategies to employ during competition.
Created by Oliver Byl of Team 4152, Hoya Robotics.

## How to Use
Configure the tasks each robot will perform and then hit the simulate button to get a breakdown of the match.
You can also configure when to use different power ups. It's recommended to first run a version of your game without powerups, then add them in according to the exact seconds you'll need them.

## Limitations/Notes
* When moving robots, the simulation does not account for collision with other robots.
* The simulation does not actively try to account for human error.
* The simulation does not account for the ability of humans to return cubes through the exchange. This may change.
* The simulation does not account for the robots' ability to fall after climbing to defeat the boss.
* In the game, the starting positions of the scale and switches are randomized. The simulation does not account for this when calulating times of delivering cubes.
* In the game, each portal has 7 cubes, but cubes are subtracted for preloading robots. The simulation assumes each robot is preloaded with 1 cube, but does not account for that in the portal cubes. The result is that the simulation has 3 more cubes in circulation that the actual game.
* The simulation assumes that every cube delivered through the exchange is placed directly into the vault.
* The simulation randomly (within certain parameters) generates the performance of the opponent alliance when considering cubes placed on switches and the scale.
  * In the future I hope to make this customizable.

Times supplied should be considered an underestimation for safety.

## Credit
Design and programming by Oliver Byl of Team 4152.
Analysis techniques based on those used by Team 254 for FIRST Steamworks.
A* search algorithm by Yan Chernikov.
