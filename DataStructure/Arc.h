//
//  Arc.h
//  Hashcode 2014
//
//  Created by Hugo BRAUN on 30/01/2015.
//  Copyright (c) 2015 Hugo BRAUN. All rights reserved.
//

#ifndef __Hashcode_2014__Arc__
#include <stdio.h>
#include "Vertex.h"
#include "Route.h"
#define __Hashcode_2014__Arc__




class Arc {
    
private:
    Vertex& start;
    Vertex& end;
    int duration;
    int distance;
    
    bool visited;
    vector<int> routes;
    int firstRoute;
public:
    Arc(Vertex& start, Vertex& end, int duration, int distance);
    
};

#endif /* defined(__Hashcode_2014__Arc__) */
