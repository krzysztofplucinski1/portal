registration flow
---
Request Body
```json
{
  "email": "string",
  "password": "string"
}
```
![Image description](flow/registration.png)

activation user flow
--
![Image description](flow/activation_user.png)

search flights
--
Request Query
```http request
?start={number}&destination={number}&dateFrom={text}&dateTo={text}&numberOfPeople={number}
```
![Image description](flow/search_flights.png)

create reservation
--
Request Body
```json
{
  "numberOfPeople": "number",
  "expressCheckIn": "boolean"
}
```
![Image description](flow/create_reservation.png)

scheduler for cancel reservation
--
![Image description](flow/scheduler_for_cancel_reservation.png)

cancel_reservation.png
--
![Image description](flow/cancel_reservation.png)
