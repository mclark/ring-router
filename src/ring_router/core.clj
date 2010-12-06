(ns ring-router.core
  (:use clout.core))

;; TODO stolen from compojure - look into how to do accreditation
(defn- method-matches
  "True if this request matches the supplied method."
  [method request]
  (let [request-method (request :request-method)
        form-method    (get-in request [:form-params "_method"])]
    (or (nil? method)
        (if (and form-method (= request-method :post))
          (= (.toUpperCase (name method)) form-method)
          (= method request-method)))))

(defn router
  "This is a handler which contains a sequence of handlers.
The first handler which returns true by being neither false nor nil
is executed and the remaining routes are ignored. Because this handler
takes a sequence of handlers as its first argument it may not work
properly with generic middleware management higher order functions."
  [handlers]
  {:pre [(sequential? handlers)]}
  (fn [req] (some #(% req) handlers)))

(defn match-route
  "Allows the contained handler to execute if the method and route of
the request match those specified by the parameters."
  ([handler method route] (match-route handler method route {}))
  ([handler method route regex-map]
     (let [r (route-compile route regex-map)
           m (keyword (.toLowerCase (name method)))]
       (fn [req]
         (if-let [route-params (route-matches r req)]
           (if (method-matches m req)
             (handler (assoc req :route-params route-params
                             :params (merge (req :params) route-params)))))))))

(defn GET
  ([route handler] (match-route handler :get route))
  ([route regex-map handler] (match-route handler :get route regex-map)))

(defn POST
  ([route handler] (match-route handler :post route))
  ([route regex-map handler] (match-route handler :post route regex-map)))

(defn PUT
  ([route handler] (match-route handler :put route))
  ([route regex-map handler] (match-route handler :put route regex-map)))

(defn DELETE
  ([route handler] (match-route handler :delete route))
  ([route regex-map handler] (match-route handler :delete route regex-map)))

(defn ANY
  ([route handler] (match-route handler nil route))
  ([route regex-map handler] (match-route handler nil route regex-map)))
