(ns processing.routes.home
  (:use compojure.core)
  (:require [processing.views.layout :as layout]
            [processing.util :as util]))

(def sketches
  {"circles"
   "/* 
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
"})

(defn home-page []
  (layout/render "home.html"
                 {:sketch "circles"
                  :sketch-source (get sketches "circles")}))

(defn get-sketch [id]
  (println id)
  (get sketches id))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/sketches/:id" [id] (get-sketch id)))
