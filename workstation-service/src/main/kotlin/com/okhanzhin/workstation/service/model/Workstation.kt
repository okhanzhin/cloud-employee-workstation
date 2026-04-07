package com.okhanzhin.workstation.service.model

data class Workstation(

	/**
     * Идентификатор рабочей станции
     **/
	val id: Long,

	/**
     * Название рабочей станции
     */
	val name: String,

	/**
     * Операционная система рабочей станции
     */
	val os: String
)

