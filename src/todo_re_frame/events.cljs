(ns todo-re-frame.events
  (:require [re-frame.core :as rf]
            [todo-re-frame.db :as db]
            [todo-re-frame.subs :refer [done?]]))

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
 ::clear-completed
 (rf/path [:todos])
 (fn [todos _]
   (let [done-ids (->> (vals todos)
                       (filter done?)
                       (map :id))]
     (reduce dissoc todos done-ids))))



(rf/reg-event-db
 ::toggle-status
 (fn [db [_ id]]
   (let [current-status (get-in db [:todos id :status])]
     (if (= current-status :done)
       (assoc-in db [:todos id :status] :active)
       (assoc-in db [:todos id :status] :done)))))

(rf/reg-event-db
 ::showing
 (fn [db [_ filter]]
   (assoc-in db [:showing] filter)))


(def generate-next-todo-id
  (rf/->interceptor
   :id      :generate-next-todo-id
   :before  (fn [context]
              (let [max-todo-id (apply max (keys (get-in context [:coeffects :db :todos])))]
                (assoc-in context [:coeffects :db :next-todo-id] ((fnil inc 0) max-todo-id))))))

(rf/reg-event-db
 ::add-todo
 [generate-next-todo-id]
 (fn [db [_ description]]
   (let [id (get-in db [:next-todo-id])
         status :active]
     (assoc-in db [:todos id] {:id id
                               :description description
                               :status status}))))

(rf/reg-event-db
 ::new-todo
 (fn [db [_ description]]
   (assoc-in db [:next-todo-description] description)))

(rf/reg-event-db
 ::clear-new-todo
 (fn [db _]
   (assoc-in db [:next-todo-description] "")))
