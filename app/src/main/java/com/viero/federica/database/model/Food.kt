package com.viero.federica.database.model

/**
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */

data class Food(var name: String? = null,
                var image: String? = null,
                var color: String? = null,
                var defaultInHome: Boolean = false) {

    var key: String? = name

}