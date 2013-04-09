# processing


- run: **lein cljsbuild auto dev** to get clojurescript compilation going
- update your deps with **lein deps**
- then launch a repl and execute **(start-server)**


## Prerequisites

- You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

- You will need PostgreSQL installed
  - Install VirtualBox and VirtualBox Extensions
  - Install Vagrant
  - clone this project git@github.com:alexguev/vagrant-postgresql.git
  - from within the folder you cloned the project into type:
    - vagrant init
    - vagrant up 
  - install the PostgreSQL client in your host computer
  - connect to the PostgreSQL using: psql -h localhost -U postgres --password
  - the password is 'password'
  - create the processing database using: CREATE DATABASE processing ENCODING 'UTF-8' LC_COLLATE='en_US.UTF8' LC_CTYPE='en_US.UTF8' TEMPLATE=template0;
  - and now you are good to go!


## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2013 FIXME
