#!/bin/bash
set -x
curl -XPUT 'http://localhost:9200/apps/'
curl -XPUT 'http://localhost:9200/apps/blog/_mapping' -d '
{
    "blog" : {
        "properties" : {
            "message" : {"type" : "string", "store" : true }
        }
    }
}
'
