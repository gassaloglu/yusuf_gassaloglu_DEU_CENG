PERSON1 = [6, 148, 72, 35, 0, 33.6, 0.627, 50]
PERSON2 = [1, 85, 66, 29, 0, 26.6, 0.351, 31]

def euclidean_distance(point1, point2)
  sum_of_squares = 0

  PERSON1.each_with_index do |value1, index|
    value2 = PERSON2[index]
    sum_of_squares += (value2 - value1) ** 2
  end

  Math.sqrt(sum_of_squares)
end

result = euclidean_distance(PERSON1, PERSON2)
puts "The Euclidean distance between #{PERSON1} and #{PERSON2} is #{result}"