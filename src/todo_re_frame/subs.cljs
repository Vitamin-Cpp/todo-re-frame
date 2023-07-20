(ns todo-re-frame.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::todos
 (fn [db _]
   (:todos db)))

(rf/reg-sub
 ::todo-list
 :<- [::todos]
 (fn [todos _]
   (vals todos)))

(defn active? [todo]
  (= (:status todo) :active))

(defn done? [todo]
  (= (:status todo) :done))

(rf/reg-sub
 ::active-todos
 :<- [::todo-list]
 (fn [todos _]
   (filter active? todos)))

(rf/reg-sub
 ::completed-todos
 :<- [::todo-list]
 (fn [todos _]
   (filter done? todos)))

(rf/reg-sub
 ::count-active-todos
 :<- [::active-todos]
 (fn [active-todos _]
   (count active-todos)))

(rf/reg-sub
 ::showing
 (fn [db _]
   (:showing db)))

(rf/reg-sub
 ::filtered-todos
 :<- [::todo-list]
 :<- [::showing]
 (fn [[todo-list showing] _]
   (cond
     (= showing :done)
     (filter done? todo-list)
     (= showing :active)
     (filter active? todo-list)
     :else
     todo-list)))

(rf/reg-sub
 ::new-todo
 (fn [db _]
   (:next-todo-description db)))

