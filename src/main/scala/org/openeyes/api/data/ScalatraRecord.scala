package org.openeyes.api.data

import org.squeryl.{KeyedEntity, PersistenceStatus}

/**
 * This trait is just a way to aggregate our model style across multiple
 * models so that we have a single point of change if we want to add
 * anything to our model behaviour
 */
trait ScalatraRecord extends KeyedEntity[Int] with PersistenceStatus {
}
