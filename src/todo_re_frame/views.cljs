(ns todo-re-frame.views
  (:require
   [todo-re-frame.subs :as subs]
   [todo-re-frame.events :as events]
   [re-frame.core :as rf]))

(defn todo [item]
  (let [id (:id item)
        description (:description item)
        status (:status item)]
    [:div.todo
     [:button.todo-status
      {:on-click #(rf/dispatch [::events/toggle-status id])
       :style {:background-color (if (= status :done)
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
      {:on-click #(rf/dispatch [::events/remove-todo id])}
      "X"]]))


(defn todo-list []
  (let [todo-list @(rf/subscribe [::subs/filtered-todos])]
    [:div.todo-list
     (for [item todo-list]
       ^{:key (:id item)} [todo item])]))

(defn footer-controls []
  [:div.footer-controls
   [:button
    {:on-click #(rf/dispatch [::events/showing :all])}
    :all]
   [:button
    {:on-click #(rf/dispatch [::events/showing :done])}
    :done]
   [:button
    {:on-click #(rf/dispatch [::events/showing :active])}
    :active]])

(defn header []
  [:div.header
   [:h2 "Todo List"]])


(defn footer []
  (let [active-todos @(rf/subscribe [::subs/count-active-todos])]
    [:div.footer
     [:p.items-togo (str active-todos " Item(s) todo")]
     [footer-controls]
     [:p.clear-completed
      {:on-click #(rf/dispatch [::events/clear-completed])}
      "clear completed"]]))


(defn main-panel []
  [:div.main
   [header]
   [todo-list]
   [footer]])
  
