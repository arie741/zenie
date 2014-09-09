(ns zenie.routes
  (:require [compojure.core :refer :all]
            [zenie.layout :as page]
            [questdb.core :refer :all]))

(def db "blogdbase")

;;Homepage

(defn homepage
  "The rendering function for homepage"
  []
  (page/render "base.html"
               {:headline "Welcome to Zenius Blog"
                :title "Zenius Blogs"
                :bcontents (map #(select-keys % [:name :id :date]) (get-docs db))}               
               ))

;;BLOG PAGE

(defn blog
  [cid]
  (page/render "blog.html"
               {:headline "Blogs woi"
                :title "Blogs"
                :bcontents (select-keys (first (find-docs db {:id cid})) [:name :id :date :uuid])}
               ))

;;POST PAGE

(defn postb []
  (page/render "post.html"
               {:headline "Post Blogs"
                :title "Blogs"}
               ))

(defn addblog [par]
  (put-doc! db par))

;;ROUTES

(defroutes home
  (GET "/" req
       (homepage))
  (GET "/blogs/:cid" [cid]
       (blog (read-string cid)))
  (GET "/postblog" req
       (postb))
  (POST "/postblog" req
       (addblog (:params req))))
