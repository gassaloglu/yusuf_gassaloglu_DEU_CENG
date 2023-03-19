#Stop words list
stopWordsList = ['able', 'about', 'above', 'abroad', 'according', 'accordingly', 'across', 'actually', 'adj', 'after', 'afterwards',
'again', 'against', 'ago', 'ahead', 'aint', 'all', 'allow', 'allows', 'almost', 'alone', 'along', 'alongside', 'already', 'also', 'although',
'always', 'am', 'amid', 'amidst', 'among', 'amongst', 'an', 'and', 'another', 'any', 'anybody', 'anyhow', 'anyone', 'anything', 'anyway', 
'anyways', 'anywhere', 'apart', 'appear', 'appreciate', 'appropriate', 'are', 'arent', 'around', 'as', 'aside', 'ask', 'asking', 'liked',
'associated', 'at', 'available', 'away', 'awfully', 'back', 'backward', 'backwards', 'be', 'became', 'because', 'become', 'becomes', 
'becoming', 'been', 'before', 'beforehand', 'begin', 'behind', 'being', 'believe', 'below', 'beside', 'besides', 'best', 'better', 
'between', 'beyond', 'both', 'brief', 'but', 'by', 'came', 'can', 'cannot', 'cant', 'caption', 'cause', 'causes', 'certain', 'certainly',
'changes', 'clearly', 'cmon', 'co', 'com', 'come', 'comes', 'concerning', 'consequently', 'consider', 'considering', 'contain', 
'containing', 'contains', 'corresponding', 'could', 'couldnt', 'course', 'cs', 'currently', 'dare', 'darent', 'definitely', 'described',
'despite', 'did', 'didnt', 'different', 'directly', 'do', 'does', 'doesnt', 'doing', 'done', 'dont', 'down', 'downwards', 'during', 
'each', 'edu', 'eg', 'eight', 'eighty', 'either', 'else', 'elsewhere', 'end', 'ending', 'enough', 'entirely', 'especially', 'et', 'etc',
'even', 'ever', 'evermore', 'every', 'everybody', 'everyone', 'everything', 'everywhere', 'ex', 'exactly', 'example', 'except', 'fairly',
'far', 'farther', 'few', 'fewer', 'fifth', 'first', 'five', 'followed', 'following', 'follows', 'for', 'forever', 'former', 'formerly', 
'forth', 'forward', 'found', 'four', 'from', 'further', 'furthermore', 'get', 'gets', 'getting', 'given', 'gives', 'go', 'goes', 'like',
'going', 'gone', 'got', 'gotten', 'greetings', 'had', 'hadnt', 'half', 'happens', 'hardly', 'has', 'hasnt', 'have', 'havent', 'having',
'he', 'hed', 'hell', 'hello', 'help', 'hence', 'her', 'here', 'hereafter', 'hereby', 'herein', 'heres', 'hereupon', 'hers', 'herself', 
'hes', 'hi', 'him', 'himself', 'his', 'hither', 'hopefully', 'how', 'howbeit', 'however', 'hundred', 'id', 'ie', 'if', 'ignored',
'ill', 'im', 'immediate', 'in', 'inasmuch', 'inc', 'indeed', 'indicate', 'indicated', 'indicates', 'inner', 'inside', 'insofar', 
'instead', 'into', 'inward', 'is', 'isnt', 'it', 'itd', 'itll', 'its', 'itself', 'ive', 'just', 'k','little', 'look', 'looking',  
'keep', 'keeps', 'kept', 'know', 'known', 'knows', 'last', 'lately', 'later', 'latter', 'latterly', 'least', 'less', 'lest', 'let',   
'looks', 'low', 'lower', 'ltd', 'made', 'mainly', 'make', 'makes', 'many', 'may', 'maybe', 'maynt', 'me', 'mean', 'meantime', 'meanwhile',
'merely', 'might', 'mightnt', 'mine', 'minus', 'miss', 'more', 'moreover', 'most', 'mostly', 'mr', 'mrs', 'much', 'must', 'mustnt', 'my',
'myself', 'name', 'namely', 'nd', 'near', 'nearly', 'necessary', 'need', 'neednt', 'needs', 'neither', 'never', 'neverf', 'neverless', 
'nevertheless', 'new', 'next', 'nine', 'ninety', 'no', 'nobody', 'non', 'none', 'nonetheless', 'noone', 'nor', 'normally', 'not', 'nothing',
'notwithstanding', 'novel', 'now', 'nowhere', 'obviously', 'of', 'off', 'often', 'oh', 'ok', 'okay', 'old', 'on', 'once', 'one', 'ones', 
'only', 'onto', 'opposite', 'or', 'other', 'others', 'otherwise', 'ought', 'oughtnt', 'our', 'ours', 'ourselves', 'out', 'outside', 'over', 
'overall', 'own', 'particular', 'particularly', 'past', 'per', 'perhaps', 'placed', 'please', 'plus', 'possible', 'presumably', 'probably', 
'provided', 'provides', 'que', 'quite', 'qv', 'rather', 'rd', 're', 'really', 'reasonably', 'recent', 'recently', 'regarding', 'regardless',
'regards', 'relatively', 'respectively', 'right', 'round', 'said', 'same', 'saw', 'say', 'saying', 'says', 'second', 'secondly', 'see',
'seeing', 'seem', 'seemed', 'seeming', 'seems', 'seen', 'self', 'selves', 'sensible', 'sent', 'serious', 'seriously', 'seven', 'several',
'shall', 'shant', 'she', 'shed', 'shell', 'shes', 'should', 'shouldnt', 'since', 'six', 'so', 'some', 'somebody', 'someday', 'somehow', 
'someone', 'something', 'sometime', 'sometimes', 'somewhat', 'somewhere', 'soon', 'sorry', 'specified', 'specify', 'specifying', 'still',
'sub', 'such', 'sup', 'sure', 'take', 'taken', 'taking', 'tell', 'tends', 'th', 'than', 'thank', 'thanks', 'thanx', 'that', 'thatll', 
'thats', 'thatve', 'the', 'their', 'theirs', 'them', 'themselves', 'then', 'thence', 'there', 'thereafter', 'thereby', 'thered', 
'therefore', 'therein', 'therell', 'therere', 'theres', 'thereupon', 'thereve', 'these', 'they', 'theyd', 'theyll', 'theyre','lets',
'theyve', 'thing', 'things', 'think', 'third', 'thirty', 'this', 'thorough', 'thoroughly', 'those', 'though', 'three', 'through',
'throughout', 'thru', 'thus', 'till', 'to', 'together', 'too', 'took', 'toward', 'towards', 'tried', 'tries', 'likely', 'likewise', 
'truly', 'try', 'trying', 'ts', 'twice', 'two', 'un', 'under', 'underneath', 'undoing', 'unfortunately', 'unless', 'unlike', 'unlikely', 
'until', 'unto', 'up', 'upon', 'upwards', 'us', 'use', 'used', 'useful', 'uses', 'using', 'usually', 'v', 'value', 'various', 'versus',
 'very', 'via', 'viz', 'vs', 'want', 'wants', 'was', 'wasnt', 'way', 'we', 'wed', 'welcome', 'well', 'went', 'were', 'werent', 'weve', 'what', 
 'whatever', 'whatll', 'whats', 'whatve', 'when', 'whence', 'whenever', 'where', 'whereafter', 'whereas', 'whereby', 'wherein', 'wheres', 
 'whereupon', 'wherever', 'whether', 'which', 'whichever', 'while', 'whilst', 'whither', 'who', 'whod', 'whoever', 'whole', 'wholl', 'whom',
'whomever', 'whos', 'whose', 'why', 'will', 'willing', 'wish', 'with', 'within', 'without', 'wonder', 'wont', 'would', 'wouldnt', 'yes', 
'yet', 'you', 'youd', 'youll', 'your', 'youre', 'yours', 'yourself', 'yourselves', 'youve', 'zero', 'a', 'hows', 'i', 'whens', 'whys', 
'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'uucp', 'w', 'x', 'y', 'z', 'I', 'www', 'amount',
'bill', 'bottom', 'call', 'computer', 'con', 'cry', 'de', 'describe', 'detail', 'due', 'eleven', 'empty', 'fifteen', 'fifty', 'fill', 
'find', 'fire', 'forty', 'front', 'full', 'give', 'herse', 'himse', 'interest', 'itse', 'mill', 'move', 'myse', 'part', 'put', 'show',
'side', 'sincere', 'sixty', 'system', 'ten', 'thick', 'thin', 'top', 'twelve', 'twenty', 'abst', 'accordance', 'act', 'added', 
'adopted', 'affected', 'affecting', 'affects', 'ah', 'announce', 'anymore', 'apparently', 'approximately', 'aren', 'arise', 'auth',
'beginning', 'beginnings', 'begins', 'biol', 'briefly', 'ca', 'date', 'ed', 'effect', 'etal', 'ff', 'fix', 'gave', 'giving', 'hid', 
'home', 'immediately', 'importance', 'important', 'index', 'information', 'invention', 'keys', 'kg', 'km', 'largely', 'line', 'll', 
'means', 'mg', 'million', 'ml', 'mug', 'na', 'nay', 'necessarily', 'nos', 'noted', 'obtain', 'obtained', 'omitted', 'ord', 'owing',
'page', 'pages', 'poorly', 'possibly', 'potentially', 'pp', 'predominantly', 'present', 'previously', 'primarily', 'promptly', 'proud',
'quickly', 'ran', 'readily', 'ref', 'refs', 'related', 'research', 'resulted', 'resulting', 'results', 'run', 'sec', 'section', 'showed', 
'shown', 'showns', 'shows', 'significant', 'significantly', 'similar', 'similarly', 'slightly', 'somethan', 'specifically', 'state', 'states',
'stop', 'strongly', 'substantially', 'successfully', 'sufficiently', 'suggest', 'thereof', 'thereto', 'thou', 'thoughh', 'thousand', 'throug',
'til', 'tip', 'ups', 'usefully', 'usefulness', 've', 'vol', 'vols', 'whim', 'widely', 'words', 'world']
# function that calculates a book word order frequency
def Word_Order_Frequency_One_Book(book_input, Word_Order, File_Output):   
    one_book = True # for write function
    bookText = "" 
    try:
        book = open(book_input , "r", encoding="utf-8")   
        bookText = book.read()
        # For remove punctiations and digits
        # The program do not delete all extended ASCIIs because it contains important letters such as latin small letter e with grave
        bookText = bookText.replace("\ufeff", "")
        bookText = bookText.replace("\n", " ")
        bookText = bookText.replace("!", " ")
        bookText = bookText.replace("'",  "")
        bookText = bookText.replace(",", " ")
        bookText = bookText.replace(".", " ")
        bookText = bookText.replace("”", "")
        bookText = bookText.replace("-", "")
        bookText = bookText.replace("(", " ")
        bookText = bookText.replace("—", "")
        bookText = bookText.replace("_", "")
        bookText = bookText.replace("“", "")
        bookText = bookText.replace(")", " ")
        bookText = bookText.replace("—", "")
        bookText = bookText.replace("*", " ")
        bookText = bookText.replace("/", " ")
        bookText = bookText.replace(":", " ")
        bookText = bookText.replace(";", " ")
        bookText = bookText.replace("?", " ")
        bookText = bookText.replace("&", " ")
        bookText = bookText.replace("’", "")
        bookText = bookText.replace("‘", "")
        bookText = bookText.replace("]"," ")             
        bookText = bookText.replace("["," ")    
        bookText = bookText.replace("$","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("0","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("1","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("2","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("3","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("4","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("5","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("6","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("7","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("8","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
        bookText = bookText.replace("9","")  
        bookText = bookText.replace("#","")   
        bookText = bookText.replace("%","")
        bookText = bookText.replace("^","")
        bookText = bookText.replace("€","")
        bookText = bookText.replace("…","")
        bookText = bookText.replace("£","")
        book.close() 
        firstBooksWordsList = [] #to store words these are not stop word
        bookText = bookText.lower()
        splittedBook = bookText.split() 
        for word in splittedBook:
            if word not in stopWordsList:
                firstBooksWordsList.append(word)
        #main part of func. 
        resultsList = {} # for store data
        for count in range(len(firstBooksWordsList)):
            if count + Word_Order <= len(firstBooksWordsList): # the program calculates every value you want
                temp1 = firstBooksWordsList[count : count + Word_Order]
                temp = " "
                temp = temp.join(temp1).rstrip().lstrip()
                if temp not in resultsList.keys():
                    resultsList.update({temp : 1})
                else:
                    resultsList[temp] += 1      
        resultsList = dict(sorted(resultsList.items(), key=lambda item: item[1], reverse=True)) #sorting data
        Writing(File_Output,resultsList,one_book) #calling write function
    # error handlings
    except FileNotFoundError:
        print(book_input, " not found.")
    except TypeError:
        print("Type Error: Unsupported operand type(s). Please try again. -> func(str, int, str)")
        print("eg. Word_Order_Frequency_One_Book('book_1.txt', 1, 'result_1_1.txt')")
    except ValueError:
        print("Type Error: Unsupported operand type(s). Please try again. -> func(str, int, str)")
        print("eg. Word_Order_Frequency_One_Book('book_1.txt', 1, 'result_1_1.txt')")
    except:
        print("ERROR!!")

# Writing function       
def Writing(file_output_name,resultList,one_book):
    printing = open(file_output_name , "w", encoding="utf-8")
    if one_book:        
        printing.write(
"""
---------------------------------------------------------
| \tWORD     \t\t| \tWORD    \t|                   
| \tORDER    \t\t| \tORDER   \t|
| \tFREQUENCY\t\t| \tSEQUENCE\t|
---------------------------------------------------------
\n""")
        for key,value in resultList.items():
            temp = [str(key), str(value)]
            printing.write("  "+"{: <40s} {: <20s}".format(*temp) + "\n") #to write well organized          
    else:
        printing.write(
"""
---------------------------------------------------------------------------------------------------------
| \tTOTAL    \t| \tBOOK 1   \t| \tBOOK 2   \t| \t\tWORD    \t|
| \tORDER    \t| \tORDER    \t| \tORDER    \t| \t\tORDER   \t|
| \tFREQUENCY\t| \tFREQUENCY\t| \tFREQUENCY\t| \t\tSEQUENCE\t|
---------------------------------------------------------------------------------------------------------
\n""")
        for element in resultList[::-1]: #writing in descending order
            printing.write("{: >11} {: >23} {: >23} {: >42}".format(*element) + "\n") #to write well organized
    printing.close()
# function that calculates 2 books word order frequency
def Word_Order_Frequency_Two_Books(book1_input,book2_input, Word_Order, File_Output):
    one_book = False #for write function
    try:
        books = [book1_input,book2_input]
        book1_results = {}
        book2_results = {}
        for selectedBook in books:
            bookText = ""    
            book = open(selectedBook, "r", encoding="utf-8")
            bookText = book.read()
            # For remove punctiations and digits
            # The program do not delete all extended ASCIIs because it contains important letters such as latin small letter e with grave
            bookText = bookText.replace("\ufeff", "")
            bookText = bookText.replace("\n", " ")        
            bookText = bookText.replace("!", " ")
            bookText = bookText.replace("'",  "")
            bookText = bookText.replace(",", " ")
            bookText = bookText.replace(".", " ")
            bookText = bookText.replace("”", "")
            bookText = bookText.replace("-", "")
            bookText = bookText.replace("(", " ")
            bookText = bookText.replace("—", "")
            bookText = bookText.replace("_", "")
            bookText = bookText.replace("“", "")
            bookText = bookText.replace(")", " ")
            bookText = bookText.replace("—", "")
            bookText = bookText.replace("*", " ")
            bookText = bookText.replace("/", " ")
            bookText = bookText.replace(":", " ")
            bookText = bookText.replace(";", " ")
            bookText = bookText.replace("?", " ")
            bookText = bookText.replace("&", " ")
            bookText = bookText.replace("’", "")
            bookText = bookText.replace("‘", "")
            bookText = bookText.replace("]"," ")             
            bookText = bookText.replace("["," ")    
            bookText = bookText.replace("$","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("0","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("1","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("2","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("3","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("4","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("5","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("6","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("7","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("8","")                                                                                                                                                                                                                                                                                                                                                                                                                                    
            bookText = bookText.replace("9","")    
            bookText = bookText.replace("#","")   
            bookText = bookText.replace("%","")
            bookText = bookText.replace("^","")
            bookText = bookText.replace("€","")
            bookText = bookText.replace("…","")
            bookText = bookText.replace("£","")
            book.close() 
            firstBooksWordsList = [] #to store words these are not stop word
            bookText = bookText.lower()
            splittedBook = bookText.split()            
            for word in splittedBook:
                if word not in stopWordsList:
                    firstBooksWordsList.append(word)
            #main part of func.
            resultsList = {} # to store data
            for count in range(len(firstBooksWordsList)):
                if count + Word_Order <= len(firstBooksWordsList):
                    temp1 = firstBooksWordsList[count : count + Word_Order]  # the program calculates every value you want
                    temp = " "
                    temp = temp.join(temp1).rstrip().lstrip()
                    if temp not in resultsList.keys():
                        resultsList.update({temp : 1})
                    else:
                        resultsList[temp] += 1
            # to store books results
            if selectedBook == book1_input:
                book1_results = resultsList 
            else:
                book2_results = resultsList
            totalWords = {} #to store all results
            totalWords.update(book2_results)
            totalWords.update(book1_results)
            res_2_book = []       
            for word in totalWords.keys():
                count1 = 0
                count2 = 0
                if word in book1_results.keys():
                    count1 = book1_results[word]
                if word in book2_results.keys():
                    count2 = book2_results[word]
                totalFreq = count2 + count1
                res_2_book.append([totalFreq, count1 ,count2, word])
            res_2_book =  sorted(res_2_book ,key=lambda x: x[0]) #sorting data
            Writing(File_Output,res_2_book,one_book) #calling write function
    #error handling
    except FileNotFoundError:
        print("File not found.")
    except TypeError:
        print("Type Error: Unsupported operand type(s). Please try again. -> func(str, str, int, str)")
        print("eg. Word_Order_Frequency_Two_Books('book_1.txt', 'book_2.txt', 1, 'result_1_1.txt')")
    except ValueError:
        print("Type Error: Unsupported operand type(s). Please try again. -> func(str, str, int, str)")
        print("eg. Word_Order_Frequency_Two_Books('book_1.txt', 'book_2.txt', 1, 'result_1_1.txt')")
    except:
        print("ERROR!!")
