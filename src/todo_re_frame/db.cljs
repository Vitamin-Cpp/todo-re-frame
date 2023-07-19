(ns todo-re-frame.db)

(def default-db
  {:todos {0 {:id 0
              :description "Register so effect and cofx"
              :status :active}
           3 {:id 3
              :description "Make sure to use interceptors somehow"
              :status :active}
           1 {:id 1
              :description "Subscribe to db event"
              :status :active}
           2 {:id 2
              :description "Emit db event for todo app"
              :status :active}}
   :showing :all})
