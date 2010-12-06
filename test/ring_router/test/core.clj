(ns ring-router.test.core
  (:use [ring-router.core] :reload)
  (:use [clojure.test]))

(deftest test-router
  (testing "simple routing"
    (let [handler (router [(fn [req] nil) (fn [req] "success1")
                           (fn [req] "success2")])]
      (is (= (handler {:params {}}) "success1")))))

(deftest test-match-route
  (testing "string route"
    (let [tester (fn [req]
                   (println "in end")
                   (is (not (nil? (req :route-params))))
                   (is (= {"resource" "widgets" "id" "42" "format" "html"}))
                   (println "returning success")
                   "success")
          handler (match-route tester :get "/:resource/:id.:format")]
      (is (= (handler {:request-method :get :uri "/widgets/42.html"}) "success"))
      (is (nil? (handler {:request-method :get :uri "/some/other/path.xml"})))
      (is (nil? (handler {:request-method :post :uri "/widgets/42.html"}))))))
