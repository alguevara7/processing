(ns processing.models.simulated)

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
  size( 100, 100 );
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

(def description "This is a simulation of the basic demographic / genealogical process. Individuals are born and individuals die. This causes lineages to branch and to disappear, and causes the population to share a common ancestor at some time in the past. The coalescent provides a mathematical description of these patterns of ancestry.")

(def all-sketches
  {"1" {:title "El Circulo 1" :description description :content sketch :liked 5 :remixed 2 :viewed 10 :author "John Doe"}
   "2" {:title "El Circulo 2" :description description :content sketch :liked 5 :remixed 2 :viewed 10 :author "John Doe"}
   "3" {:title "El Circulo 3" :description description :content sketch :liked 5 :remixed 2 :viewed 10 :author "John Doe"}})
