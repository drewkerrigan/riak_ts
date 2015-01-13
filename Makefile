.PHONY: deps

all: deps
	./rebar compile

deps:
	./rebar get-deps

clean:
	./rebar clean

distclean: clean
	./rebar delete-deps

cleantest:
	rm -rf .eunit/*test*

test: all cleantest
	./rebar skip_deps=true eunit

retest: cleantest
	./rebar skip_deps=true eunit

itest: all cleantest
	INTEGRATION_TEST=true ./rebar skip_deps=true eunit

reitest: cleantest
	INTEGRATION_TEST=true ./rebar skip_deps=true eunit