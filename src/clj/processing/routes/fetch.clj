(ns processing.routes.fetch
  (:use [compojure.core]
        [service.fetch.remotes :only [call-remote safe-read defremote]]))

(def sketch "/* 
  PROCESSINGJS.COM - BASIC EXAMPLE
  Delayed Mouse Tracking  
  MIT License - Hyper-Metrix.com/F1LT3R
  Native Processing compatible 
*/  

// Global variables
float radius = 50.0;
int X, Y;
int nX, nY;
int delay = 16;

// Setup the Processing Canvas
void setup(){
  size( 200, 200 );
  strokeWeight( 10 );
  frameRate( 15 );
  X = width / 2;
  Y = width / 2;
  nX = X;
  nY = Y;  
}

// Main draw loop
void draw(){
  
  radius = radius + sin( frameCount / 4 );
  
  // Track circle to new destination
  X+=(nX-X)/delay;
  Y+=(nY-Y)/delay;
  
  // Fill canvas grey
  background( 100 );
  
  // Set fill-color to blue
  fill( 0, 121, 184 );
  
  // Set stroke-color white
  stroke(255); 
  
  // Draw circle
  ellipse( X, Y, radius, radius );                  
}


// Set circle's next destination
void mouseMoved(){
  nX = mouseX;
  nY = mouseY;  
}
")

(def all-sketches
  {"1" {:title "El Circulo 1" :content sketch}
   "2" {:title "El Circulo 2" :content sketch}
   "3" {:title "El Circulo 3" :content sketch}})

(defn get-sketches []
  (->> (seq all-sketches)
       (map (fn [[k v]] {:id k :title (:title v)}))
       vec))

(get-sketches)

(defn get-sketch [id]
  (println "get-sketch " id "string? " (string? id))
  (get (get all-sketches id) :content))

(defremote sketches []
  (get-sketches))

(defroutes fetch-routes
  (ANY "/_fetch" [params remote]
    (let [params (safe-read params)
          remote (keyword remote)]
      (call-remote remote params)))
  (GET "/sketch/:id" [id] (get-sketch id)))