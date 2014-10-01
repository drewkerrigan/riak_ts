FORMAT: 1A
HOST: http://www.google.com

# riak_ts
Riak TS is a time series interface that runs on Riak.

# Group Time Series Collections

## Collections [/ts/collections]
A Collection is a group of `Time Series Objects`

### List all Collections [GET]
+ Response 200 (application/json)

        [{
          "collection": "temperatures", 
          "description": "Thermostat temperature reports",
          "dimensions": ["YYYYMMDDHHmm"]
        }, {
          "collection": "locations", 
          "description": "GPS Location Updates",
          "dimensions": ["YYYYMMDDHHmm", "location"]
        }]

## Collection [/ts/collections/{collection}]
A single `Time Series Collection` with all its meta data

The Collection resource payload has the following attributes:

| Name  | Types  | Examples  | Description  |
|---|---|---|---|
| description  | string  | `Thermostat temperature reports`  | A simple human readable descripton of the collection  |
| dimensions  | [string]  | [`YYYY`, `YYYYMM`, `YYYYMMDD`, `YYYYMMDDHH`, `YYYYMMDDHHmm`, `YYYYMMDDHHmmSS`]  | The dimensions you intend to query the data with (i.e. the granularity with which to store data)  |

+ Parameters
    + collection (required, string, `temperatures`) ... String `collection` of the Collection to perform action with.

### Retrieve a Collection [GET]
+ Response 200 (application/json)

        {
          "collection": "temperatures", 
          "description": "Thermostat temperature reports",
          "dimensions": ["YYYYMMDDHHmm"]
        }

### Create a Collection [POST]

+ Request (application/json)

        {
          "description": "Thermostat temperature reports",
          "dimensions": ["YYYYMMDDHHmm"]
        }

+ Response 204


### Remove a Collection [DELETE]
+ Response 204

# Group Time Series Objects

## Object [/ts/collections/{collection}/objects/{object}]
A single `Time Series Object` with all its details

+ Parameters
    + collection (required, string, `temperatures`) ... String `collection` of the database to perform action with.
    + object (required, integer, `201409231046`) ... Integer `object` of the `Time Series Collection` to perform action with.

### Retrieve an Object [GET]
+ Response 200 (application/json)

        {
          "object": "201409231046", 
          "metrics": [75]
        }

### Create an Object [POST]
+ Request (application/json)

        {
          "object": "201409231046", 
          "metrics": [75]
        }

+ Response 204


### Remove an Object [DELETE]
+ Response 204

# Group Time Series Queries

## Query [/ts/collections/{collection}/query/{?start,end}]
A single `Time Series Object` with all its details

+ Parameters
    + collection (required, string, `temperatures`) ... String `collection` of the database to perform action with.
    + start (required, integer, `201409231040`) ... Integer `start` of the `Time Series Query`.
    + end (required, integer, `201409231050`) ... Integer `end` of the `Time Series Query`.

### Perform a Query [GET]
+ Response 200 (application/json)

        [{
          "object": "201409231046", 
          "metrics": [75]
        },{
          "object": "201409231047", 
          "metrics": [76]
        },{
          "object": "201409231048", 
          "metrics": [77]
        }]