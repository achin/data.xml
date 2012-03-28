;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for building XML elements from vectors"
      :author "Alex Chin"}
  clojure.data.xml.test-elements
  (:use [clojure.test :only [deftest is are]]
        [clojure.data.xml :only [element tag-attr-body elements]]))

(deftest test-simple
  (is (= (element :foo)
         (elements [:foo])))

  (is (= (element :foo {:attr "bar attr value"})
         (elements [:foo {:attr "bar attr value"}])))

  (is (= (element :foo {:attr "bar attr value"} "content")
         (elements [:foo {:attr "bar attr value"} "content"])))

  (is (= (element :foo {} nil)
         (elements [:foo {} nil]))))

(deftest test-tag-attrs-body-parsing
  (is (= {:tag :foo, :attrs {:attr "1"}, :body '("content")}
         (tag-attr-body [:foo {:attr "1"} "content"])))

  (is (= {:tag :foo, :attrs {:attr "1"}, :body '()}
         (tag-attr-body [:foo {:attr "1"}])))

  (is (= {:tag :foo, :attrs {:attr "1"}, :body '("content-1" "content-3")}
         (tag-attr-body [:foo {:attr "1"} "content-1" nil "content-3"])))

  (is (= {:tag :foo, :attrs {}, :body '()}
         (tag-attr-body [:foo])))

  (is (= {:tag :foo, :attrs {}, :body '("content-1" "content-2" "content-3")}
         (tag-attr-body [:foo "content-1" "content-2" "content-3"])))

  (is (= {:tag :foo, :attrs {}, :body '("content-1" "content-3")}
         (tag-attr-body [:foo "content-1" nil "content-3"]))))

(deftest test-optional-attr-map
  (is (= (element :foo {} "content")
         (elements [:foo "content"])))

  (is (= (element :foo {} "content-1" "content-2" "content-3")
         (elements [:foo "content-1" "content-2" "content-3"]))))

(deftest
  nesting
  (is (= (element :foo {} (element :bar))
         (elements [:foo [:bar]])))

  (is (= (element :foo {}
                  (element :bar)
                  (element :baz)
                  (element :bat))
         (elements [:foo [:bar] [:baz] [:bat]])))

  (is (= (element :foo {}
                  (element :bar {}
                           (element :baz {}
                                    (element :bat))))
         (elements [:foo [:bar [:baz [:bat]]]]))))
