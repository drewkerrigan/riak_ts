# Riak Time-Series API


## Goal

The goal of a time-series API on Riak is to solve a common problem faced by customers.  The API should at minimum expose a way to easily store individual events as well as a way to query for multiple events given user defined dimensions.

Some examples of rollup dimensions are `YYYYMMDDhhmm` for one minute of data, `YYYYMM` for one month of data, etc.  A nice feature to have would be precalculated data-aware dimensions such as the sum or average value of a field in a JSON document.  The most basic implementation however, would be an iteration over many individual events effectively providing a multi-get function based on a date range.
 

## Background

Some example use cases that justify the need for such an interface on Riak are:

* "Internet of things" style applications such as temperature records from smart thermostats
* Any other automatically collected metric based on a time interval such as system monitoring tools like Boundary or New Relic server monitor agents
* Time window based queries for chat room logs

Some of these types of problems can be solved with a simple Solr index on a timestamp field in a document; but traditional indexes simply won't perform at the scale that many Riak users require.
 

## Overview

### How should it be accomplished?

There are multiple approaches that can provide a solution to the goals listed above:

#### Stand-alone application running along side Riak nodes

Based on extremely simple proofs of concept made for various clients on various technology stacks, it would be fairly trivial to create a simple standalone application that implements the basic requirements of a time-series API.  A JVM solution might be the best technology choice based on the eventual goals of Riak architecture (3.0 JVM based plugins / addons).  This approach is also attractive because the work done in the near term has a greater chance of reusability by many customers.

**Pros**

* Short time to market assuming minimum viable product for requirements

**Cons**

* Relatively few features until a large development effort is given to implement more advanced features 

#### Extend an existing time-series project

One well known time-series interface implementation is [Open TSDB](http://opentsdb.net/).  A Riak backend does not currently exist, but it would be possible to write one to replace the HBASE integration points currently used in Open TSDB.

**Pros**

* Much more feature rich once the implementation of a Riak backend is complete

**Cons**

* Likely to be a much larger development effort to extend Open TSDB to use Riak instead of HBASE for all storage and indexing calls

### Specific Requirements

At minimum, the following would be required:

* Ability to store a timestamped piece of data at multiple time granularities
* Ability to specify multiple dimensions with which to retrieve data for a specified bucket or group of events (minimally different granularities of time rollups)
* Ability to query on a specific dimension using start and end markers

Eventually, the following would be nice to have:

* Ability to specify document aware dimensions such as running statistics on one or more fields found in a document
* Ability to retrieve data in specific formats that are graph friendly or some-other-consumer friendly


## Current State

No real development work has begun on this project.  A sample API mockup has been created on Apiary though: [http://docs.riakts.apiary.io/](http://docs.riakts.apiary.io/).


## Feedback Requested:

* Does it make sense to implement this interface on Riak?
* Should we instead suggest another solution such as Open TSDB on HBASE?
* Has anyone already developed similar work other than one-off PoC's for clients?
