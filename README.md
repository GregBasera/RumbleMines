# PROJECT
---
| Name | RumbleMines |
|------|-------|
| Description | A multiplayer version for Minesweeper. Written in Java in the Linux environment using sockets |
| Author | Basera, Greg Emerson *gbasera@gbox.adnu.edu.ph* |

# ENVIRONMENT
---
* Linux (Ubuntu)
* Java

# LET'S PLAY
---
* First of all, you should clone this repo.
  * git clone https://github.com/GregBasera/RumbleMines.git RumbleMines

  * *Note: The use of two PCs is optimal for this game, but it can be played with just one.*

  * *Note: You'll need a seperate terminal to run Server.java*

* Set up the **Server** so the clients can connect to it. Run the commands below in a terminal.
  * $ javac Server.java

  * $ java Server [port] [row] [col] [numOfPlayers]

| Attribute | Description | Allowed Values |
|---|---|
| port | the port number to Server is going to be initialized in | 5000 < *n* < 1024 |
| row, col | the dimensions the board will be generated with when the Clients connects | *n* > 5 |
| numOfPlayers | the number of Clients that will be allowed to connect to the Server | *n* > 1 |

* Initialize a **Client** and connect to the server. *Note: The game will not start until the [numOfPlayers] set by the server is met.*
  * $ javac Client.java

  * $ java Client [ip] [port] [IGN]

| Attribute | Description | Allowed Values |
|---|---|
| ip | The IP address of the **Server** | *Ex.* 127.0.0.1 |
| port | the port number to Server is initialized in | 5000 < *n* < 1024 |
| IGN | (In-Game Name) the name you want in-game. *Note: Use quotation marks if you want spaces in your name* | *Ex.* "Greg" |

* Enjoy!
