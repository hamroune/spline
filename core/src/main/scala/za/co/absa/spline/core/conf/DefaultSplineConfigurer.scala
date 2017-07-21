/*
 * Copyright 2017 Barclays Africa Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.absa.spline.core.conf


import org.apache.commons.configuration._
import za.co.absa.spline.persistence.api.PersistenceFactory
import za.co.absa.spline.persistence.atlas.AtlasPersistenceFactory
import za.co.absa.spline.persistence.mongo.MongoPersistenceFactory

/**
  * The object contains static information about default settings needed for initialization of the library.
  */
object DefaultSplineConfigurer {
  val mongoDbUrlKey = "spline.mongodb.url"
  val mongoDbNameKey = "spline.mongodb.name"
  val persistenceTypeKey = "spline.persistence.type"
}

/**
  * The class represents default settings needed for initialization of the library.
  *
  * @param conf A source of settings
  */
class DefaultSplineConfigurer(conf: Configuration) extends SplineConfigurer {

  import DefaultSplineConfigurer._
  import za.co.absa.spline.common.ConfigurationImplicits._

 override lazy val persistenceFactory = {
   val persistenceType = conf getRequiredString persistenceTypeKey
   persistenceType toLowerCase match {
     case "mongo" | "mongodb" => new MongoPersistenceFactory(
       dbUrl = conf getRequiredString mongoDbUrlKey,
       dbName = conf getRequiredString mongoDbNameKey
     )
     case "atlas" => new AtlasPersistenceFactory()
     case _ => throw new ConfigurationException(s"'$persistenceType' is not valid value for the '$persistenceTypeKey' property.")
   }
 }
}
