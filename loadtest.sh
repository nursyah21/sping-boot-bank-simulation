# you can get this curl in generate code in bruno
# this test is pass


# curl --request GET \
#   --url 'http://localhost:8000/api/transaction?keyword=' \
#   --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzU5NzI0MjY0LCJleHAiOjE3NTk3Mjc4NjR9.qkfIEj_FgcHSfKgiusxAFynPLQMqaW76RTi3pK2cuAM

# curl --request POST \
#   --url http://localhost:8000/api/transaction \
#   --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1OTY5MjQyMCwiZXhwIjoxNzU5Njk2MDIwfQ.kgIekjRM7EF5XBNJzeCUuK7NLKj9bBAlr6i_DoPWlkY' \
#   --header 'bearer: ' \
#   --header 'content-type: application/json' \
#   --data '{
#   "amount": "10000000",
#   "destinationId": "1733731752"
# }'


TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1OTkwNzg4OCwiZXhwIjoxNzU5OTExNDg4fQ.GL_l30iyIeDRoJd1VWvY-tej3PciHSd2ye2IV_wn6J4"
CONCURRENT=60
DURATION=1m

# raw test check health springboot (for warming up)
bombardier -c ${CONCURRENT}  -d ${DURATION} http://localhost:8000/api/health

# raw test check health quarkus (for warming up)
bombardier -c ${CONCURRENT} -d ${DURATION} http://localhost:8001/api/health

# read test database
bombardier -H "Authorization: Bearer ${TOKEN}" -c ${CONCURRENT} -d ${DURATION}  http://localhost:8000/api/transaction

# # write test database
bombardier \
  -m POST \
  -b '{"amount": "10000000", "destinationId": "3621891983"}' \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  http://localhost:8000/api/transaction \
  -c ${CONCURRENT} \
  -d ${DURATION}