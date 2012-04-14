(ns misquote.core)

(defn- listish?
  "Gets both lists and lazy seqs."
  [ob]
  (and (sequential? ob) (not (vector? ob))))

(defn- unquote-form?
  [form]
  (and (listish? form)
       (= 'clojure.core/unquote (first form))))

(defn- unquote-splicing-form?
  [form]
  (and (listish? form)
       (= 'clojure.core/unquote-splicing (first form))))

(def ^:private scalar?
  (some-fn number? true? false? nil? keyword? symbol? string?))

(defmacro misquote
  [arg]
  (cond

   (unquote-form? arg)
   (second arg)

   (vector? arg)
   `(vec (misquote ~(seq arg)))

   (set? arg)
   `(set (misquote ~(seq arg)))

   (map? arg)
   `(into {} (misquote ~(map vec arg)))
   
   (listish? arg)
   (cons `concat
         (for [x arg]
           (cond (unquote-form? x)
                 [(second x)]

                 (unquote-splicing-form? x)
                 (second x)

                 :else
                 [(list `misquote x)])))

   (scalar? arg) (list 'quote arg)))