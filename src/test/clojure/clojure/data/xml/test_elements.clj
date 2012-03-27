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
        [clojure.data.xml :as xml :only [element elements->]]))

(deftest
  simple
  (is (= (xml/element :foo)
         (elements-> [:foo])))

  (is (= (xml/element :foo {:attr "bar attr value"})
         (elements-> [:foo {:attr "bar attr value"}])))

  (is (= (xml/element :foo {:attr "bar attr value"} "content")
         (elements-> [:foo {:attr "bar attr value"} "content"])))

  (is (= (xml/element :foo {} nil)
         (elements-> [:foo {} nil]))))
