#include "HttpClient/HttpClient.h"

/**
 *  Start configuration
 * 
 */
 
unsigned int nextTime = 0;    // Next time to contact the server
HttpClient http;

const int GREEN_LED_PIN = A4;
const int RED_LED_PIN = A5;

double light = 0;
int ledValue = 0;
int redValue = 0;
int greenValue = 0;

// Headers currently need to be set at init, useful for API keys etc.
http_header_t headers[] = {
    //  { "Content-Type", "application/json" },
    //  { "Accept" , "application/json" },
    { "Accept" , "*/*"},
    { NULL, NULL } // NOTE: Always terminate headers will NULL
};

http_request_t request;
http_response_t response;

/**
 *  End of configuration
 * 
 */


void setup() {

    pinMode(GREEN_LED_PIN, OUTPUT);
    pinMode(RED_LED_PIN, OUTPUT);

}

void loop() {
    
    if (nextTime > millis()) {
        return;
    }

    light = retrieveLight();
    setLEDLight(light);
    
    nextTime = millis() + 1800000;
}

double retrieveLight(){
    
    request.hostname = "dropcube-nefele.rhcloud.com";
    request.port = 80;
    request.path = "/api/lights/particle/" + System.deviceID();

    // The library also supports sending a body with your request:
    //request.body = "{\"key\":\"value\"}";

    // Get request
    http.get(request, response, headers);
    
    return atof(response.body);
    
}

void setLEDLight(double &value){
    
    ledValue = value * 255;
    
    if(ledValue == 0){
        
        redValue = 0;
        greenValue = 0;
        
    }else{
        
        greenValue = 255-ledValue;
        redValue = ledValue;
        
    }
    
    analogWrite(GREEN_LED_PIN, greenValue);
    analogWrite(RED_LED_PIN, redValue);
    
    Particle.variable("light", value);
    Particle.variable("green", greenValue);
    Particle.variable("red", redValue);
    
}