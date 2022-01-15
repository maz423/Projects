#! /usr/bin/bash

#Name : Mohammad Aman Zargar
#NSID : Maz423
#Student id : 11265940
#instructor Mark Eramian

# START PROVIDED TO STUDENTS
FILENAME='../music.txt'

# File column numbers (starts at 1 instead of 0)
ENTRY_NUMBER_COL=1
ALBUM_TYPE_COL=2
ALBUM_NAME_COL=3
ARTIST_COL=4
FEAT_ARTIST_COL=5
SONG_NAME_COL=6
GENRE_COL=7
RELEASE_YEAR_COL=8
PREV_URL_COL=9

# Main Music Genres
PUNK="punk"
POP="pop"
INDIE="indie"
ROCK="rock"

# This funciton is a black box.  Under no circumstances are you to edit it.
# Furthermore, it is a black box because if something in this function was not taught to you in the textbook
# or in the background section for this assignment, do not use it.  In otherwords, everything you need is in
# the assignment specifications and textbook.
create_directory_structure() {
    IFS=$'\n'   # input field separator (or internal field separator)
    FILE_GENRES=( `cat $FILENAME | cut -d $'\t' -f $GENRE_COL | sort -u ` )
    
    for GENRE in ${FILE_GENRES[@]}; do 
        if [[ $GENRE == "Genre" ]]; then
            continue
        fi

        IFS=' '
        read -ra GENRE_SPLIT <<< $GENRE
        GENRE=`echo "$GENRE" | tr ' ' '-' `
        COUNT=1
        FOUND=1
        NUM_SPLIT="${#GENRE_SPLIT[@]}"

        for SPLIT in ${GENRE_SPLIT[@]}; do
            if [[ $SPLIT == *$INDIE* ]] && [[ ${GENRE_SPLIT[*]} != $INDIE ]]; then
                PARENT_GENRE=$INDIE
            elif [[ $SPLIT == *$PUNK* ]] && [[ ${GENRE_SPLIT[*]} != $PUNK ]]; then
                PARENT_GENRE=$PUNK
            elif [[ $SPLIT == *$POP* ]] && [[ ${GENRE_SPLIT[*]} != $POP ]]; then
                PARENT_GENRE=$POP
            elif [[ $SPLIT == *$ROCK* ]] && [[ ${GENRE_SPLIT[*]} != $ROCK ]]; then
                PARENT_GENRE=$ROCK
            else
                if [ "$GENRE" = "None" ]; then
                    mkdir -p "uncategorized"
                    break
                elif [[ $FOUND -eq 0 ]]; then   
                    if [[ $COUNT -eq $NUM_SPLIT ]]; then                        
                        break
                    fi
                    
                    (( COUNT+=1 ))
                    continue
                elif [ $COUNT -ne $NUM_SPLIT ] && [ $FOUND -eq 1 ]; then
                    (( COUNT+=1 ))
                    continue
                else
                    mkdir -p "${GENRE}"
                    continue
                fi
            fi

            mkdir -p "${PARENT_GENRE}/${GENRE}"
            FOUND=0
            (( COUNT+=1 ))
        done
    done
}
# END PROVIDED TO STUDENTS

# TODO: everything after this point

#crating Music directory.
if test ! -d "music"
then 
	echo "Creating  music  directory."
	mkdir music
	
fi

cd music 

create_directory_structure

#reading music.txt skipping the first line and storing each coulmn entry in a variable.
cat $FILENAME | tail -n +2 | while IFS=$'\t' read -r ENTRY_NUMBER ALBUM_TYPE ALBUM_NAME ARTIST FEAT_ARTIST SONG_NAME GENRE RELEASE_YEAR PREV_URL
do      
        #modifying variables as required.
	GENRE=`echo ${GENRE,,} | tr " " "-"`
	ARTIST=`echo ${ARTIST,,} | tr " " "-"`
	FEAT_ARTIST=`echo ${FEAT_ARTIST} | tr " " "_"`
	ALBUM_NAME=`echo ${ALBUM_NAME} | tr " " "_"`
	if test $FEAT_ARTIST != None
	then
		SONG_NAME=`echo ${SONG_NAME}_\(ft._$FEAT_ARTIST\)`
	fi
	
	#if block for creating uncategorized directory
	if test  $GENRE = none
	then
		if test ! -d "uncategorized"
		then 
			echo "creating uncategorized directory"
			mkdir uncategorized
		fi
        #if GENRE is not none.
	else 
		ls | while IFS=$'\t' read -r MUSIC
		do 
		    if test $MUSIC = $GENRE
		    then
		        if test ! -d "$MUSIC/$ARTIST"
		    	then
		    		mkdir $MUSIC/$ARTIST
		    	fi
		        #create txt file to store song if GENRE matches a folders in the music directory.
		        echo $SONG_NAME:$PREV_URL >> ${MUSIC}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		    
		    #create txt in uncategorized if GENRE dosen't match folders in music directory.
		    else 
		        if test ! -d "uncategorized/$ARTIST"
		        then 
		        	mkdir uncategorized/$ARTIST
		        else
		    		echo $SONG_NAME:$PREV_URL > uncategorized/$ARTIST/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		        fi
		        
		    fi
		    
		  done
         	 
         	 #check for popular sub-categories pop , rock ,indie ,punk 
		 if [[ "$GENRE" == *"pop-"* || "$GENRE" == *"-pop"* ]]
		    then
		        
		        if test ! -d "pop/$GENRE/$ARTIST"
		    	then
		    		mkdir pop/$GENRE/$ARTIST
		    		
		    	fi
		    	
		    	echo $SONG_NAME:$PREV_URL >> pop/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		    	#if test ! -f pop/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		        #then
		   		#echo $SONG_NAME:$PREV_URL >> pop/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		    	 
		    	
		  fi     #fi
		 
		  if  [[ "$GENRE" == *"rock-"* || "$GENRE" == *"-rock"* ]]
		      then
		    	  if test ! -d "rock/$GENRE/$ARTIST"
		    	  then
		    		mkdir rock/$GENRE/$ARTIST
		    	  fi
		    	  echo $SONG_NAME:$PREV_URL >> rock/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		    	 # if test ! -f rock/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		            # then
		                # echo $SONG_NAME:$PREV_URL >> rock/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		  fi  	  #fi
		  if  [[ "$GENRE" == *"indie-"* || "$GENRE" == *"-indie"* ]]
		      then
		    	  if test ! -d "indie/$GENRE/$ARTIST"
		    	  then
		    		mkdir indie/$GENRE/$ARTIST
		    	  fi
		   	  echo $SONG_NAME:$PREV_URL >> indie/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		  fi
		    	  
		  if  [[ "$GENRE" == *"punk-"* || "$GENRE" == *"-punk"* ]]
		      then
		    	  if test ! -d "punk/$GENRE/$ARTIST"
		    	  then
		    		mkdir punk/$GENRE/$ARTIST
		    	  fi
		    	  echo $SONG_NAME:$PREV_URL >> punk/${GENRE}/${ARTIST}/${RELEASE_YEAR}_-_${ALBUM_NAME}_\($ALBUM_TYPE\).txt
		    	
		    
		  fi
        fi 

done 

cd -
zip -r Music.zip music > zip-output.txt
echo "operation complete"





