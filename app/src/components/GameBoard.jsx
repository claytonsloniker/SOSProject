import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/App.css';

const GameBoard = () => {
    const [board, setBoard] = useState([]);
    const [currentPlayer, setCurrentPlayer] = useState('GREEN');
    const [size, setSize] = useState(4); // Default board size set to 4
    const [inputSize, setInputSize] = useState(4); // Input field for board size set to 4
    const [gameMode, setGameMode] = useState('simple'); // Default game mode
    const [selectedLetter, setSelectedLetter] = useState('S'); // Default letter

    useEffect(() => {
        createGame(size, gameMode);
    }, []);

    const createGame = async (size, gameMode) => {
        try {
            await axios.post('/api/game/create', null, { params: { size, gameMode } });
            await fetchBoard();
            await fetchCurrentPlayer();
        } catch (error) {
            console.error('Error creating game:', error);
        }
    };

    const fetchBoard = async () => {
        try {
            const response = await axios.get('/api/game/board');
            setBoard(response.data);
        } catch (error) {
            console.error('Error fetching board:', error);
        }
    };

    const fetchCurrentPlayer = async () => {
        try {
            const response = await axios.get('/api/game/player');
            setCurrentPlayer(response.data);
        } catch (error) {
            console.error('Error fetching current player:', error);
        }
    };

    const makeMove = async (row, col) => {
        try {
            await axios.post('/api/game/move', null, { params: { row, col, letter: selectedLetter } });
            await fetchBoard();
            await fetchCurrentPlayer();
        } catch (error) {
            console.error('Error making move:', error);
        }
    };

    const handleInputChange = (event) => {
        setInputSize(event.target.value);
    };

    const handleGameModeChange = (event) => {
        setGameMode(event.target.value);
    };

    const handleLetterChange = (letter) => {
        setSelectedLetter(letter);
    };

    const handleStartGame = () => {
        const newSize = parseInt(inputSize, 10);
        setSize(newSize);
        createGame(newSize, gameMode);
    };

    const getPlayerColor = (player) => {
        return player === 'RED' ? 'red' : 'green';
    };

    return (
        <div className="game">

            <div className="player-select">
                <h1>SOS Game</h1>
                <label>
                    Board Size:
                    <input
                        type="number"
                        value={inputSize}
                        onChange={handleInputChange}
                        min="3"
                        max="10"
                    />
                </label>
            </div>
            <div className="game-mode-select">
                <label>
                    <input
                        type="radio"
                        value="simple"
                        checked={gameMode === 'simple'}
                        onChange={handleGameModeChange}
                    />
                    Simple Game
                </label>
                <label>
                    <input
                        type="radio"
                        value="general"
                        checked={gameMode === 'general'}
                        onChange={handleGameModeChange}
                    />
                    General Game
                </label>
            </div>
            <div className="board-wrapper">
                <div className="board-container">
                    <h2 className="current-player">
                        Current Player: <span
                        style={{color: getPlayerColor(currentPlayer)}}>{currentPlayer || 'Loading...'}</span>
                    </h2>
                    <div className="board" style={{gridTemplateColumns: `repeat(${size}, 50px)`}}>
                        {board.map((row, rowIndex) => (
                            row.map((cell, colIndex) => (
                                <div
                                    key={`${rowIndex}-${colIndex}`}
                                    onClick={() => makeMove(rowIndex, colIndex)}
                                    className="cell"
                                >
                                    {cell}
                                </div>
                            ))
                        ))}
                    </div>
                </div>
            </div>
            <div className="letter-select-and-new-game">
                <div className="letter-select">
                    <label
                        className={`radio-label-button ${selectedLetter === 'S' ? 'selected' : ''}`}
                        onClick={() => handleLetterChange('S')}
                    >
                        S
                    </label>
                    <label
                        className={`radio-label-button ${selectedLetter === 'O' ? 'selected' : ''}`}
                        onClick={() => handleLetterChange('O')}
                    >
                        O
                    </label>
                </div>
                <div className="new-game-button-container">
                    <button onClick={handleStartGame}>New Game</button>
                </div>
            </div>
        </div>
    );
};

export default GameBoard;