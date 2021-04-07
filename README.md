# DeACoudre

DeACoudre is a minecraft mini games plugin. Players have one objective, fill the pool without break their knees !

## Features

- Create multiple DAC games in one minecraft server
- Easy configuration by using in-game commands
- [SOON] Compatibility with PlaceHolderAPI

## Create DAC

To create a DeACoudre follow carrefully steps bellow.

1. #### First create new DAC ####
    `/dac create myDAC`

2. #### Set displayname for your new DAC - [OPTIONAL] ####
    `/dac set displayname &dMy&eDAC`

3. #### Set lobby location ####
    `/dac set lobby`
    > This will set the lobby location by using your actual location in game where you run the command.

4. #### Set pool location ####
    `/dac set pool`
    > This will set the pool location by using your actual location in game where you run the command.
  
5. #### Set diving location ####
    `/dac set diving`
   > This will set the diving location by using your actual location in game where you run the command.
  
6. #### Set spectator location ####
    `/dac set spectator`
   > This will set the spectator location by using your actual location in game where you run the command.
    
7. #### Set end location ####
    `/dac set end`
   > This will set the end location by using your actual location in game where you run the command.
    
8. #### Set pool region ####
    This command is in two steps, you have to select two opposite corner of your pool and run these commands:
    - `/dac set pool-region pos1` ➤  First corner
      > This will set the pos1 location by using your actual location in game where you run the command.
    - `/dac set pool-region pos2` ➤ Second corner
        > This will set the pos2 location by using your actual location in game where you run the command.
    
9. #### Set win points ####
    `/dac set win-points 1`

10. #### Set minimum players ####
    `/dac set min-players 2`

11. #### Set maximum players ####
    `/dac set max-players 6`

12. #### Restart your server ####
    Be sure you have follow all steps before restart. When you restart your server check the server terminal and look for debug message.

13. #### Link your DAC with a sign ####
    > Be sure you are looking a sign before perform this command.
    
    `/dac set sign myDAC`
