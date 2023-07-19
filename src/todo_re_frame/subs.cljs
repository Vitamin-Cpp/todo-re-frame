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
   
