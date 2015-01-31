//
//  Arc.cpp
//  Hashcode 2014
//
//  Created by Hugo BRAUN on 30/01/2015.
//  Copyright (c) 2015 Hugo BRAUN. All rights reserved.
//

#include "Arc.h"


Arc::Arc(Vertex& _start, Vertex& _end, int _duration, int _distance, bool _bidirectional) :
        start(_start), end(_end), duration(_duration),
        distance(_distance), routes(vector<int>()), bidirectional(_bidirectional)  {
    visited = false;
    firstRoute = UNDEFINED_ROUTE;
}
