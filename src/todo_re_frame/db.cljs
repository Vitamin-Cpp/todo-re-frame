(ns todo-re-frame.db)

(def default-db
  {:todos {}
   :next-todo-id 0
   :next-todo-description ""
   :showing :all})
