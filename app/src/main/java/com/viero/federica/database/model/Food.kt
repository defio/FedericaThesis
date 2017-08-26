package com.viero.federica.database.model

/**
 * This software has been developed by Ennova Research S.r.l.<br/>
 * <br/>
 * Project: federica<br/>
 * <br/>
 * created on: 2017-08-15
 *
 * @author Nicola De Fiorenze
 */

data class Food(var name: String? = null,
                var image: String? = null,
                var color: String? = null,
                var defaultInHome: Boolean = false
)