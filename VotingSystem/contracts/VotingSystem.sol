pragma solidity ^0.8.0;

contract VotingSystem {
    struct Topic {
        string title;
        string[] options; // Limiting options to 100
        address[] registeredVoters; // Array to store registered voters' addresses
        uint256[] numberOfVotes; // Array to store the number of votes for each option
        address[] activeVoters; // Array to store active voters for this topic
        bool started; // Flag to indicate if voting has started
        bool finished; // Flag to indicate if voting has finished
    }
	
	struct TopicInfo {
		uint256 id;
		string title;
	}
	
	struct TopicDetails {
		uint256 id;
		string title;
		OptionDetails[] options;
		bool started;
		bool finished;
	}
	
	struct OptionDetails {
		uint256 id;
		string option;
		uint256 numberOfVotes;
    }
	
	struct TopicWithOptionInfo {
		uint256 id;
		string title;
		OptionInfo[] options;
	}
	
	struct OptionInfo {
		uint256 id;
		string name;
	}

    Topic[] public topics;

    event TopicAdded(string topic);
    event OptionAdded(uint256 indexed topicIndex, string option);
    event VoterRegistered(uint256 indexed topicIndex, address voterAddress);
    event VotingStarted(uint256 indexed topicIndex);
    event VotingFinished(uint256 indexed topicIndex);
    event Voted(uint256 indexed topicIndex, address indexed voter, string indexed option);

    function addTopic(string memory _topic) external {
        require(bytes(_topic).length > 0, "Topic title must not be empty");

        // Initialize options array
        string[] memory emptyOptions;
        address[] memory emptyVoters;
        uint256[] memory emptyNumberOfVotes;
        address[] memory emptyActiveVoters;

        topics.push(Topic({
            title: _topic,
            options: emptyOptions, // Initialize options array
            registeredVoters: emptyVoters, // Initialize registeredVoters array
            numberOfVotes: emptyNumberOfVotes, // Initialize number of votes array
            activeVoters: emptyActiveVoters, // Initialize active voters array
            started: false, // Initialize started flag
            finished: false // Initialize finished flag
        }));

        emit TopicAdded(_topic);
    }

    function addOption(uint256 _topicIndex, string memory _option) external {
        require(_topicIndex < topics.length, "Invalid topic index");
        require(bytes(_option).length > 0, "Option name must not be empty");
        require(topics[_topicIndex].options.length < 100, "Max options reached for this topic");
        require(!topics[_topicIndex].started, "Voting has already started for this topic");

        topics[_topicIndex].options.push(_option);

        // Initialize vote count for the new option
        topics[_topicIndex].numberOfVotes.push(0);

        emit OptionAdded(_topicIndex, _option);
    }

    function registerVoter(uint256 _topicIndex, address _voterAddress) external {
        require(_topicIndex < topics.length, "Invalid topic index");
        require(_voterAddress != address(0), "Invalid voter address");
        require(!topics[_topicIndex].started, "Voting has already started for this topic");

        // Check if voter address already registered for this topic
        for (uint256 i = 0; i < topics[_topicIndex].registeredVoters.length; i++) {
            require(topics[_topicIndex].registeredVoters[i] != _voterAddress, "Voter already registered");
        }

        topics[_topicIndex].registeredVoters.push(_voterAddress);

        emit VoterRegistered(_topicIndex, _voterAddress);
    }

    function startVoting(uint256 _topicIndex) external {
        require(_topicIndex < topics.length, "Invalid topic index");
        require(!topics[_topicIndex].started, "Voting has already started for this topic");

        topics[_topicIndex].started = true;

        emit VotingStarted(_topicIndex);
    }

    function finishVoting(uint256 _topicIndex) external {
        require(_topicIndex < topics.length, "Invalid topic index");
        require(topics[_topicIndex].started, "Voting has not started for this topic");
        require(!topics[_topicIndex].finished, "Voting has already finished for this topic");

        topics[_topicIndex].finished = true;

        emit VotingFinished(_topicIndex);
    }

	 function vote(uint256 _topicIndex, uint256 _optionIndex) external {
		require(_topicIndex < topics.length, "Invalid topic index");
		require(topics[_topicIndex].started, "Voting has not started for this topic");
		require(!topics[_topicIndex].finished, "Voting has already finished for this topic");
		require(isRegisteredVoter(_topicIndex, msg.sender), "You are not registered to vote for this topic");
		require(_optionIndex < topics[_topicIndex].options.length, "Invalid option index");

		// Check if the voter has already voted for this topic
		require(!hasVoted(_topicIndex, msg.sender), "You have already voted for this topic");

		// Increment the number of votes for the option
		topics[_topicIndex].numberOfVotes[_optionIndex]++;

		// Add the voter to the active voters
		topics[_topicIndex].activeVoters.push(msg.sender);

		emit Voted(_topicIndex, msg.sender, topics[_topicIndex].options[_optionIndex]);
	}


    function getTopicCount() external view returns (uint256) {
        return topics.length;
    }

    function getTopic(uint256 _topicIndex) external view returns (string memory, uint256, bool, bool) {
        require(_topicIndex < topics.length, "Invalid topic index");
        return (topics[_topicIndex].title, topics[_topicIndex].options.length, topics[_topicIndex].started, topics[_topicIndex].finished);
    }

    function getOption(uint256 _topicIndex, uint256 _optionIndex) external view returns (string memory) {
        require(_topicIndex < topics.length, "Invalid topic index");
        require(_optionIndex < topics[_topicIndex].options.length, "Invalid option index");
        return topics[_topicIndex].options[_optionIndex];
    }

    function isRegisteredVoter(uint256 _topicIndex, address _voterAddress) internal view returns (bool) {
        require(_topicIndex < topics.length, "Invalid topic index");
        for (uint256 i = 0; i < topics[_topicIndex].registeredVoters.length; i++) {
            if (topics[_topicIndex].registeredVoters[i] == _voterAddress) {
                return true;
            }
        }
        return false;
    }

    function hasVoted(uint256 _topicIndex, address _voterAddress) internal view returns (bool) {
        require(_topicIndex < topics.length, "Invalid topic index");
        for (uint256 i = 0; i < topics[_topicIndex].activeVoters.length; i++) {
            if (topics[_topicIndex].activeVoters[i] == _voterAddress) {
                return true;
            }
        }
        return false;
    }

    function findOptionIndex(uint256 _topicIndex, string memory _option) internal view returns (uint256) {
        require(_topicIndex < topics.length, "Invalid topic index");
        for (uint256 i = 0; i < topics[_topicIndex].options.length; i++) {
            if (keccak256(bytes(topics[_topicIndex].options[i])) == keccak256(bytes(_option))) {
                return i;
            }
        }
        revert("Option not found");
    }

    function getVoteCount(uint256 _topicIndex, string memory _option) external view returns (uint256) {
        require(_topicIndex < topics.length, "Invalid topic index");
        require(bytes(_option).length > 0, "Option must not be empty");
        uint256 optionIndex = findOptionIndex(_topicIndex, _option);
        return topics[_topicIndex].numberOfVotes[optionIndex];
    }
	
	function getTopicsWithDetails() external view returns (TopicDetails[] memory) {
		TopicDetails[] memory allTopicsWithDetails = new TopicDetails[](topics.length);

		for (uint256 i = 0; i < topics.length; i++) {
			string[] memory topicOptions = topics[i].options;
			uint256[] memory topicNumberOfVotes = topics[i].numberOfVotes;
			uint256 numOptions = topicOptions.length;

			OptionDetails[] memory optionDetails = new OptionDetails[](numOptions);

			for (uint256 j = 0; j < numOptions; j++) {
				optionDetails[j] = OptionDetails({
					id: j,
					option: topicOptions[j],
					numberOfVotes: topicNumberOfVotes[j]
				});
			}

			allTopicsWithDetails[i] = TopicDetails({
				id: i,
				title: topics[i].title,
				options: optionDetails,
				started: topics[i].started,
				finished: topics[i].finished
			});
		}

		return allTopicsWithDetails;
	}
	
	
	function getTopicsOnly() external view returns (TopicInfo[] memory) {
		TopicInfo[] memory allTopics = new TopicInfo[](topics.length);

		for (uint256 i = 0; i < topics.length; i++) {
			allTopics[i] = TopicInfo({
				id: i,
				title: topics[i].title
			});
		}

		return allTopics;
	}
	
	function getTopicWithOptions(uint256 _topicIndex) external view returns (TopicWithOptionInfo memory) {
		require(_topicIndex < topics.length, "Invalid topic index");

		string[] memory topicOptions = topics[_topicIndex].options;
		uint256 numOptions = topicOptions.length;

		OptionInfo[] memory allOptions = new OptionInfo[](numOptions);

		for (uint256 i = 0; i < numOptions; i++) {
			allOptions[i] = OptionInfo({
				id: i,
				name: topicOptions[i]
			});
		}

		return TopicWithOptionInfo({
			id: _topicIndex,
			title: topics[_topicIndex].title,
			options: allOptions
		});
	}
}
