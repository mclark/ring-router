(ns ring-router.route)

(defn not-found [body]
  (fn [req]
    {:status 404 :body body}))