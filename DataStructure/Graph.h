#ifndef GRAPH_H
#define GRAPH_H

#include "Vertex.h"
#include "Arc.h"
#include "Route.h"
#include <vector>
#include <string>

using namespace std;

class Graph
{
public:
    Graph(string filepath);
    void buildVertex(double longitude, double latitude);
    void buildArc(int start_vertex, int end_vertex, bool bidirectional, int distance, int duration);
    void defineStartingVertex(int starting_vertex);
private:
    vector<Vertex> vertices;
    vector<Arc> arcs;
    vector<Route> routes;
    int number_vertices;
    int number_arcs;
    int max_time;
    int number_cars;
    Vertex& starting_vertex;
};

#endif // GRAPH_H
