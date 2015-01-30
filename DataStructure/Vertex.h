//
//  Vertex.h
//  Hashcode 2014
//
//  Created by Hugo BRAUN on 30/01/2015.
//  Copyright (c) 2015 Hugo BRAUN. All rights reserved.
//

#ifndef __Hashcode_2014__Vertex__
#define __Hashcode_2014__Vertex__

#include <stdio.h>
#include <vector> 

using namespace std;

class Arc;

class Vertex {
public:
    Vertex();
private:
    float lat;
    float lng;
    vector<Arc> arcs;
};

#endif /* defined(__Hashcode_2014__Vertex__) */
