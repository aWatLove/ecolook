
# Таблицы
## Пользователи
User
- id
- username
- firstname
- lastname
- email
- phone
- password
- role_Id

## Role
Roles
- id
- name

## Места
Places
- id
- title
- description
- CoordinateX
- CoordinateY
- photo (URLS фотографий)
- is_del


## Тарифы
Tariffs
- id
- title
- description
- price_per_day
- photo (URLS фотографий)
- is_del

## Заказы
Orders
- id
- user_Id
- place_Id
- tariff_id
- date_start
- date_end
- days_count
- status
- payment_status
- total_price

## Теги
Tags
- id
- name
- is_del

## Опции
Additonal Options
- id
- title
- price
- is_del

## Order_add_options
- id
- order_id
- option_id
- count

## Place_tags
- id
- place_id
- tag_id

## Place_tariffs
- id
- place_id
- tariff_id

## Change_order
- id
- order_id
- operator_id
- old_status
- new_status
- date


**Enum Status**:
- Обрабатывается
- В пути
- Доставлено
- Отменено
- Ошибка

**Enum payment status**:
- Оплачено
- Не оплачено

![diagram](tables.drawio 3.png)

# Эндпоинты 

## Авторизация
### POST sign up
`POST /api/auth/signup`

input:
```json
{
	"username":"username",
	"firstname":"firstname",
	"lastname":"lastname",
	"email":"useremail@mail.ru",
	"phone":"89528125252",
	"password":"password12345"
}
```
output:
```json
{
  "message": "User registered successfully!"
}
```

### POST sign in
`POST /api/auth/signin`

input:
```json
{
	"username":"username",
	"password":"password12345"
}
```
output:
```json
{
	"token":"eyJhbGciOiJIUzUxMiJ9",
    "type": "Bearer",
    "id": 3,
    "username": "username",
    "firstname": "username",
    "lastname": "username", 
    "email": "username@asd.ru",
    "role": "ROLE_USER"
}
```

## Пользователь
### GET users info
`GET /api/user/info`
**auth**

output:
```json
{
	"username":"username",
	"firstname":"firstname",
	"lastname":"lastname",
	"email":"useremail@mail.ru",
	"phone":"89528125252"
}
```


### PATCH update user details
`PATCH /api/user`
**auth**

input:
```json
{
	"username":"username",
	"firstname":"firstname",
	"lastname":"lastname",
	"email":"useremail@mail.ru",
	"phone":"89528125252"
}
```
output:
```json
{
	"username":"username",
	"firstname":"firstname",
	"lastname":"lastname",
	"email":"useremail@mail.ru",
	"phone":"89528125252"
}
```

## Place
### GET all place previews around
`GET /api/place`
**auth**

Input: 
Query:
- Xcoordinate
- Ycoordinate
- radius - в километрах (default 50)
- tags фильтр по тегам делать? 

Output:
```json
{
	"places":[
		{
			"id":0, 
			"title":"some place 0",
			"XCoordinate":56.2853,
			"YCoordinate":42.0971,
			"tags":[
				"Горный отдых", 
				"у реки"
			]
		  },
	  { 
			"id":1, 
			"title":"some place 1", 
			"XCoordinate":36.2853, 
			"YCoordinate":52.0971,
			"tags":[
				"у реки", 
				"В лесу"
			]
		}
	]
}
```

### GET place by id
`GET /api/place/:id
**auth**

Input: id - int
Output:
```json
{
	"id":0,
	"title":"some place", 
	"description":"some descriptin", 
	"XCoordinate":52.5252,
	"YCoordinate":34.4333,
	"tags":[
		"tag1", 
		"tag2", 
		"tagde"
	], 
	"tariffs":[
		{
			"id":1,
			"title":"title1",
			"description":"desc",
			"price_per_day":5000, 
			"photo":"url" 
		}
	], 
	"photos":[
		"url1", 
		"url2", 
		"url3"
	]
}
```

### POST create place
`POST /admin/api/place`
**auth admin**

Input:
```json
{
	"title":"new plave title",
	"description":"new description", 
	"XCoordinate":52.0808,
	"YCoordinate":12.1489,
	"tags":[1, 5, 4], 
	"tariffs":[1, 2, 3, 4, 7],
	"photos":[
		"url1", 
		"url2", 
		"url3"
	]
}
```

Output:
```json
{
	"id":0,
	"title":"some place", 
	"description":"some descriptin", 
	"XCoordinate":52.5252,
	"YCoordinate":34.4333,
	"tags":[
		"tag1", 
		"tag2", 
		"tagde"
	], 
	"tariffs":[
		{
			"id":1,
			"title":"title1",
			"description":"desc",
			"price_per_day":5000, 
			"photo":"url" 
		}
	], 
	"photos":[
		"url1", 
		"url2", 
		"url3"
	]
}
```

 
### PATCH update place info
`PATCH /admin/api/place/:id`
**auth admin**
`id - int`

Input:
```json
{
	"title":"new plave title",
	"description":"new description", 
	"XCoordinate":52.0808,
	"YCoordinate":12.1489,
	"tags":[1, 5, 4], 
	"tariffs":[1, 2, 3, 4, 7],
	"photos":[
		"url1", 
		"url2", 
		"url3"
	]
}
```

Output:
```json
{
	"id":0,
	"title":"some place", 
	"description":"some descriptin", 
	"XCoordinate":52.5252,
	"YCoordinate":34.4333,
	"tags":[
		"tag1", 
		"tag2", 
		"tagde"
	], 
	"tariffs":[
		{
			"id":1,
			"title":"title1",
			"description":"desc",
			"price_per_day":5000, 
			"photo":"url" 
		}
	], 
	"photos":[
		"url1", 
		"url2", 
		"url3"
	]
}
```

### DELETE place
`DELETE /admin/api/place/:id`
**auth admin**

Input: `id - int`

Удаляться из бд не будет
Soft delete

Output: 200 ok

## Orders
### POST create order
`POST /api/order`
**auth**

input:
```json
{
	"place_id":1,
	"days_count":5,
	"tarrif_id":0,
	"optional_ids":[0,2]
}
```

output:
```json
{
	"id":0,
	"user":{
		"id":0,
		"username":"username0",
		"firstname":"vlad",
		"lastname":"suvorov",
		"phone":"89528120252"
	},
	"place":{
		"id":0,
		"title":"sometitle",
		"description":"description",
		"XCoordinate":52.2552,
		"YCoordinate":52.5252
	},
	"tariff":{
		"id":0,
		"title":"title",
		"description":"description",
		"price_per_day":5000
	},
	"days_count":5,
	"status":"Обрабатывается",
	"date_start":"2017-03-12T13:37:27+00:00",
	"date_end":"2017-03-17T13:37:27+00:00",
	"additional_oprions":[
		{
			"id":0,
			"title":"opt0",
			"price":200,
			"count":1
		},
		{
			"id":2,
			"title":"opt2",
			"price":100,
			"count":4
		}
	],
	"total_price":25600,
	"payment_status":"Не оплачено"
}
```

### GET order by id
`GET /api/order/:id`

input: id - int,

output:
```json
{
	"id":0,
	"user":{
		"id":0,
		"username":"username0",
		"firstname":"vlad",
		"lastname":"suvorov",
		"phone":"89528120252"
	},
	"place":{
		"id":0,
		"title":"sometitle",
		"description":"description",
		"XCoordinate":52.2552,
		"YCoordinate":52.5252
	},
	"tariff":{
		"id":0,
		"title":"title",
		"description":"description",
		"price_per_day":5000
	},
	"days_count":5,
	"status":"Обрабатывается",
	"date_start":"2017-03-12T13:37:27+00:00",
	"date_end":"2017-03-17T13:37:27+00:00",
	"additional_oprions":[
		{
			"id":0,
			"title":"opt0",
			"price":200,
			"count":1
		},
		{
			"id":2,
			"title":"opt2",
			"price":100,
			"count":4
		}
	],
	"total_price":25600,
	"payment_status":"Не оплачено"
}
```

### GET user order previews
`GET /api/order/user/:username`

input: username - string

output:
```json
{
	"orders":[
		{
			"id":0,
			"place":{
				"id":0,
				"title":"title"
			},
			"status":"На месте",
			"payment_status":"Оплачено",
			"days_count":5,
			"total_price":25600
		},
		{
			"id":1,
			"place":{
				"id":1,
				"title":"title"
			},
			"status":"В пути",
			"payment_status":"Оплачено",
			"days_count":2,
			"total_price":16300
		}
	]
}
```

### PATCH поменять статус order'а
`PATCH /admin/api/order/:id/process`
**auth admin**

query: 
- status


### PATCH отменить заказ
`PATCH /api/order/:id/cancel`
**auth**

input: id - int

output:
ok или не ок


### Оплата, посмотреть сервисы оплаты и как это можно обработать на беке
-> меняется статус оплаты в Order'е

### GET all tariffs
`GET /api/tariff`
input: -
output:
```json
{
	"tariffs":[
		{
			"id":0,
			"title":"title0",
			"description":"description0",
			"price_per_day":500,
			"photo":"url0"
		},
		{
			"id":1,
			"title":"title1",
			"description":"description1",
			"price_per_day":400,
			"photo":"url1"
		},
		{
			"id":2,
			"title":"title2",
			"description":"description2",
			"price_per_day":50000,
			"photo":"url2"
		}
	]
}
```

### POST create tariff
`POST /admin/api/tariff`
**auth admin**

input:
```json
{
	"title":"title2",
	"description":"description2",
	"price_per_day":50000,
	"photo":"url2"
}
```

output:
```json
{
	"id":2,
	"title":"title2",
	"description":"description2",
	"price_per_day":50000,
	"photo":"url2"
}
```

### PATCH update tariff
`PATCH /admin/api/tariff/:id`

input: id - int
```json
{
	"title":"title2",
	"description":"description2",
	"price_per_day":50000,
	"photo":"url2"
}
```

output:
```json
{
	"id":2,
	"title":"title2",
	"description":"description2",
	"price_per_day":50000,
	"photo":"url2"
}
```

### DELETE delete tariff
`DELETE /admin/api/tariff/:id`

input: id - int

output: ok 200


### GET all optionals
`GET /api/optional`

input: -

output:
```json
{
	"optionals":[
		{
			"id":0,
			"title":"title0",
			"price":200
		},
		{
			"id":1,
			"title":"title1",
			"price":100
		},
		{
			"id":2,
			"title":"title2",
			"price":400
		}
	]
}
```

### POST create optional
`POST /admin/api/optional`
**auth admin**

input:
```json
{
	"title":"title",
	"price":200
}
```

output:
```json
{
	"id":2,
	"title":"title2",
	"price":400
}
```


### PATCH update optional
`PATCH /admin/api/optional/:id`
**auth admin**

input: id - int
```json
{
	"title":"title",
	"price":200
}
```

output:
```json
{
	"id":2,
	"title":"title2",
	"price":400
}
```

### DELETE optional
`DELETE /admin/api/optional/:id`

input: id - int
output: ok


### GET all tags
`GET /api/tag`

input: - 
output:
```json
{
	"tags":[
		{
			"id":0,
			"name":"tag0"
		},
		{
			"id":1,
			"name":"tag1"
		},
		{
			"id":2,
			"name":"tag2"
		}
	]
}
```

### POST tag
`POST /admin/api/tag`
**auth admin**
input:
```json
{
	"name":"tag1"
}
```

output:
```json
{
	"id":1,
	"name":"tag1"
}
```

### DELETE tag
`DELETE /admin/api/tag/:id`
**auth admin**

input: id - int 
output:
200 ok