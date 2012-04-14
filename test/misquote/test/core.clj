(ns misquote.test.core
  (:use [misquote.core])
  (:use [clojure.test]))


(deftest template-test
  (are [expr result] (= expr result)

       (misquote (foo ~8))
       '(foo 8)
       
       (let [x 12] (misquote (foo ~x ~238 bar)))
       '(foo 12 238 bar)

       (misquote (bam ~@[3 4 5] booze))
       '(bam 3 4 5 booze)

       (misquote ([weeze ~'okay ~(str 3 4 5)]))
       '([weeze okay "345"])

       (let [x "twelve" y [3 4 'eight]]
         (misquote {foo bar ~x #{~y ~x not-quoted}}))
       '{foo bar "twelve" #{"twelve" not-quoted [3 4 eight]}}

       (let [x "okay"]
         (misquote [3 4 ~@(list :four (misquote #{7 8 ~x}))]))
       [3 4 :four #{8 "okay" 7}]))
