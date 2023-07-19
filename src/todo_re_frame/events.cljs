(ns todo-re-frame.events
  (:require [re-frame.core :as rf]
            [todo-re-frame.db :as db]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 ::edit-todo-description
 (fn [db [_ id description]]
   (assoc-in db [:todos id :description] description)))

(rf/reg-event-db
 ::remove-todo
 (fn [db [_ id]]
   (update-in db [:todos] dissoc id)))

(rf/reg-event-db
 ::toggle-status
 (fn [db [_ id]]
   (let [current-status (get-in db [:todos id :status])]
     (if (= current-status :done)
       (assoc-in db [:todos id :status] :active)
       (assoc-in db [:todos id :status] :done)))))
