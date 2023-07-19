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
