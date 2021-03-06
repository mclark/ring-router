# ring-router

Ring Router is a middleware for Ring based on Clout. It is designed
to let you do routing of Ring handlers and only return the results
of the first one that returns non nil.

This is designed to mimic the way Compojure handles request
dispatching but without depending on a macro based DSL.
Basically, if you like the idea of building your web app's
central dispatch using an easy to read macro based DSL,
then you should probably skip this library.

However, if you like the sound of fully composable handlers
all the way down to your app's code then you've come to the
right place.

## Usage

add this to your project.clj dependencies

	[ring-router "0.2-SNAPSHOT"]

a simple code sample

	(router
	 [(match-route handler1 :get "products/:id.html")
	  (match-route handler2 :put "products/:id.html")
	  (match-route handlerN nil "/*")])

a more complete example can be found here:

	https://github.com/marianoguerra/immutant-recipes/tree/master/ring-router-example

## License

Copyright (C) 2010 Matt Clark

Distributed under the Eclipse Public License, the same as Clojure.
