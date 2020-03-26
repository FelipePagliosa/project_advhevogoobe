package br.project_advhevogoober_final.Model

class OfficeProfile(name:String?=null,val phone:String?=null,val businessId:String?=null,var messagees:MutableList<String>?=null,email:String?=null):User(name,null,null,null,email)