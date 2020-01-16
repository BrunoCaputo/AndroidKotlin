package com.bruno.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnClick(view: View) {
        val btnSelected = view as Button
        endGame = 0
        val cellId: Int = when (btnSelected.id) {
            R.id.button1 -> 1
            R.id.button2 -> 2
            R.id.button3 -> 3
            R.id.button4 -> 4
            R.id.button5 -> 5
            R.id.button6 -> 6
            R.id.button7 -> 7
            R.id.button8 -> 8
            R.id.button9 -> 9
            else -> 0
        }

        playGame(cellId, btnSelected)
    }

    var activePlayer = 1
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var pl1Wins = 0
    var pl2Wins = 0
    var endGame = 0

    private fun playGame(cellId: Int, btnSelected: Button) {
        if (activePlayer == 1) {
            btnSelected.text = "X"
            btnSelected.setBackgroundResource(R.color.blue)
            player1.add(cellId)
            activePlayer = 2
            autoPlay()
        } else {
            btnSelected.text = "O"
            btnSelected.setBackgroundResource(R.color.darkGreen)
            player2.add(cellId)
            activePlayer = 1
        }

        btnSelected.isEnabled = false

        checkWinner()
    }

    private fun checkWinner() {
        var winner = -1

        //Player 1
        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            winner = 1
        } else if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            winner = 1
        } else if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            winner = 1
        } else if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            winner = 1
        } else if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            winner = 1
        } else if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            winner = 1
        } else if (player1.contains(1) && player1.contains(5) && player1.contains(9)) {
            winner = 1
        } else if (player1.contains(3) && player1.contains(5) && player1.contains(7)) {
            winner = 1
        }

        //Player 2
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            winner = 2
        } else if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            winner = 2
        } else if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            winner = 2
        } else if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            winner = 2
        } else if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            winner = 2
        } else if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            winner = 2
        } else if (player2.contains(1) && player2.contains(5) && player2.contains(9)) {
            winner = 2
        } else if (player2.contains(3) && player2.contains(5) && player2.contains(7)) {
            winner = 2
        }

        if (winner == 1) {
            Toast.makeText(this, "Player 1 won the game", Toast.LENGTH_LONG).show()
            pl1Wins++
            play1WinsLbl.text = pl1Wins.toString()
            clearGame()
        } else if (winner == 2) {
            Toast.makeText(this, "Player 2 won the game", Toast.LENGTH_LONG).show()
            pl2Wins++
            play2WinsLbl.text = pl2Wins.toString()
            clearGame()
        }
    }

    private fun clearGame() {
        endGame = 1
        activePlayer = 1
        player1.clear()
        player2.clear()

        Thread.sleep(500)
        for (cellId in 1..9) {
            val btnSelected: Button? = when (cellId) {
                1 -> button1
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> button1
            }

            btnSelected!!.text = ""
            btnSelected.setBackgroundResource(R.color.whiteButton)
            btnSelected.isEnabled = true
        }
    }

    private fun autoPlay() {
        if (endGame != 1) {
            val emptyCells = ArrayList<Int>()


            for (cellId in 1..9) {
                if (!player1.contains(cellId) || player2.contains(cellId)) {
                    emptyCells.add(cellId)
                }
            }

            if (emptyCells.isEmpty()) {
                Toast.makeText(this, "Nobody won the game", Toast.LENGTH_LONG).show()
                clearGame()
            }

            if (activePlayer == 2) {
                val r = Random()
                val randIndex = r.nextInt(emptyCells.size)
                val cellId = emptyCells[randIndex]

                val btnSelected: Button?
                btnSelected = when (cellId) {
                    1 -> button1
                    2 -> button2
                    3 -> button3
                    4 -> button4
                    5 -> button5
                    6 -> button6
                    7 -> button7
                    8 -> button8
                    9 -> button9
                    else -> button1
                }

                playGame(cellId, btnSelected)
            }
        }
    }
}
