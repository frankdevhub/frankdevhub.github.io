---
layout: post
title:  "MathsGame"
categories: Matlab Python
tags:  Matlab Python
author: Franklinfang
---

* content
{:toc}

## MathGame

<iframe  
 height=560 
 width=90% 
 src="https://test.i9rht.com/static/index.html"  
 frameborder=0  
 allowfullscreen>
 </iframe>


```

    // 绘制碰撞球体实体
    function drawBall(x, y, color) {
      ctx.beginPath();
      ctx.arc(x, y, SQUARE_SIZE / 2, 0, Math.PI * 2, false);
      ctx.fillStyle = color;
      ctx.fill();
      ctx.closePath();
    }

    function drawSquares() {
      for (let i = 0; i < numSquaresX; i++) {
        for (let j = 0; j < numSquaresY; j++) {
          ctx.fillStyle = squares[i][j];
          ctx.fillRect(
            i * SQUARE_SIZE,
            j * SQUARE_SIZE,
            SQUARE_SIZE,
            SQUARE_SIZE
          );
        }
      }
    }

    // 触碰事件触发后更新矩阵内的色块，AB两个阵营中根据触碰的角度计算变化的数量
    function updateSquareAndBounce(x, y, dx, dy, color) {
      let updatedDx = dx;
      let updatedDy = dy;

      for (let angle = 0; angle < Math.PI * 2; angle += Math.PI / 4) {
        let checkX = x + Math.cos(angle) * (SQUARE_SIZE / 2);
        let checkY = y + Math.sin(angle) * (SQUARE_SIZE / 2);

        let i = Math.floor(checkX / SQUARE_SIZE);
        let j = Math.floor(checkY / SQUARE_SIZE);

        if (i >= 0 && i < numSquaresX && j >= 0 && j < numSquaresY) {
          if (squares[i][j] !== color) {
            squares[i][j] = color;

            if (Math.abs(Math.cos(angle)) > Math.abs(Math.sin(angle))) {
              updatedDx = -updatedDx;
            } else {
              updatedDy = -updatedDy;
            }
          }
        }
      }

      return { dx: updatedDx, dy: updatedDy };
    }

    function updateScoreElement() {
      let dayScore = 0;
      let nightScore = 0;
      for (let i = 0; i < numSquaresX; i++) {
        for (let j = 0; j < numSquaresY; j++) {
          if (squares[i][j] === DAY_COLOR) {
            dayScore++;
          } else if (squares[i][j] === NIGHT_COLOR) {
            nightScore++;
          }
        }
      }

      scoreElement.textContent = `A ${dayScore} | B ${nightScore}`;
    }

    function checkBoundaryCollision(x, y, dx, dy) {
      if (x + dx > canvas.width - SQUARE_SIZE / 2 || x + dx < SQUARE_SIZE / 2) {
        dx = -dx;
      }
      if (
        y + dy > canvas.height - SQUARE_SIZE / 2 ||
        y + dy < SQUARE_SIZE / 2
      ) {
        dy = -dy;
      }

      return { dx: dx, dy: dy };
    }

    function draw() {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      drawSquares();

      drawBall(x1, y1, DAY_BALL_COLOR);
      let bounce1 = updateSquareAndBounce(x1, y1, dx1, dy1, DAY_COLOR);
      dx1 = bounce1.dx;
      dy1 = bounce1.dy;

      drawBall(x2, y2, NIGHT_BALL_COLOR);
      let bounce2 = updateSquareAndBounce(x2, y2, dx2, dy2, NIGHT_COLOR);
      dx2 = bounce2.dx;
      dy2 = bounce2.dy;

      let boundary1 = checkBoundaryCollision(x1, y1, dx1, dy1);
      dx1 = boundary1.dx;
      dy1 = boundary1.dy;

      let boundary2 = checkBoundaryCollision(x2, y2, dx2, dy2);
      dx2 = boundary2.dx;
      dy2 = boundary2.dy;

      x1 += dx1;
      y1 += dy1;
      x2 += dx2;
      y2 += dy2;

      iteration++;
      if (iteration % 1_000 === 0) console.log("iteration", iteration);

      updateScoreElement();

      requestAnimationFrame(draw);
    }
    
	// 重置动画切换画面
    requestAnimationFrame(draw);

```