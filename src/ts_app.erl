
%% -------------------------------------------------------------------
%%
%% Copyright (c) 2015 Basho Technologies, Inc.
%%
%% This file is provided to you under the Apache License,
%% Version 2.0 (the "License"); you may not use this file
%% except in compliance with the License.  You may obtain
%% a copy of the License at
%%
%%   http://www.apache.org/licenses/LICENSE-2.0
%%
%% Unless required by applicable law or agreed to in writing,
%% software distributed under the License is distributed on an
%% "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
%% KIND, either express or implied.  See the License for the
%% specific language governing permissions and limitations
%% under the License.
%%
%% -------------------------------------------------------------------

-module(ts_app).
-behaviour(application).
-export([start/2, stop/1]).

%%%===================================================================
%%% Callbacks
%%%===================================================================

start(_StartType, _StartArgs) ->
    Enabled = true,
    case Enabled of
        true -> add_routes();
        _ -> ok
    end,
    
    ts_sup:start_link().

start(_StartType, _StartArgs) ->
    Enabled = true,
    case ts_sup:start_link(Enabled) of
        {ok, Pid} ->
            maybe_setup(Enabled),
            {ok, Pid};
        Error ->
            Error
    end.

stop(_State) ->
    ok = riak_api_pb_service:deregister([{ts_pb_object, 30, 31}]),
    ok = riak_api_pb_service:deregister([{ts_pb_query, 70, 71}]),
    ok.

%% @private
maybe_setup(false) ->
  ok;
maybe_setup(true) ->
  setup_http(),
  setup_pb().

%% @private
setup_http() ->
    [webmachine_router:add_route(R) || R <- http_routes()].

%% @private
http_routes() ->
     [{["types", bucket_type, "buckets", bucket, "timeseries"], fun is_post/1,
      ts_wm_object, [{api_version, 3}]},
     {["types", bucket_type, "buckets", bucket, "timeseries", key],
      ts_wm_object, [{api_version, 3}]},
     {["types", bucket_type, "buckets", bucket, "timeseries", "query"],
      ts_wm_query, [{api_version, 3}]}].

%% @private
setup_pb() ->
    ok = riak_api_pb_service:register([{ts_pb_object, 30, 31}]),
    ok = riak_api_pb_service:register([{ts_pb_query, 70, 71}]),
    ok.

%% @private
is_post(Req) ->
    wrq:method(Req) == 'POST'.