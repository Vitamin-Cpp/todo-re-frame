(ns todo-re-frame.views
  (:require
   [re-frame.core :as re-frame]
   [todo-re-frame.subs :as subs]
   [todo-re-frame.events :as events]
   [re-frame.core :as rf]))

(defn todo [item]
  (let [id (:id item)
        description (:description item)
        status (:status item)]
    [:div.todo
     [:button.todo-status
      {:style {:background-color (if (= status :done)
                                   "rgb(200, 240, 200)"
                                   "rgb(253, 245, 234)")}}
      status]
     [:input.todo-description
      {:type :text
       :value description
       :on-change (fn [e]
                    (let [current-description (-> e .-target .-value)]
                      (rf/dispatch [::events/edit-todo-description id current-description])))}]
     [:button.remove
      "X"]]))


(defn todo-list []
  (let [todo-list @(rf/subscribe [::subs/todo-list])]
    [:div.todo-list
     (for [item todo-list]
       ^{:key (:id item)} [todo item])]))

(defn footer-controls []
  [:div.footer-controls
   [:button.active-filter :all]
   [:button :done]
   [:button :active]])

(defn header []
  [:div.header
   [:h2 "Todo List"]])


(defn footer []
  [:div.footer
   [:p.items-togo "3 Items todo"]
   [footer-controls]
   [:p.clear-completed "clear completed"]])


(defn main-panel []
  [:div.main
   [header]
   [todo-list]
   [footer]])
  
