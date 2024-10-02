package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Drive")
public class RobotRelativeDrive extends LinearOpMode {

    private MotorEx frontLeft;
    private MotorEx backLeft;
    private MotorEx backRight;
    private MotorEx frontRight;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(MotorEx.class, "fL");
        frontRight = hardwareMap.get(MotorEx.class, "fR");
        backLeft = hardwareMap.get(MotorEx.class, "bL");
        backRight = hardwareMap.get(MotorEx.class, "bR");

        frontLeft.setInverted(true);
        backLeft.setInverted(true);


        waitForStart();     // pauses until start button is pressed

        while(opModeIsActive()) {
            double leftY = -gamepad1.left_stick_y;
            double leftX = gamepad1.left_stick_x;
            double rightX = gamepad1.right_stick_x;

            // converting to wheel speeds
             double frontLeftSpeed = leftY + leftX + rightX;        // left y is always added because it's consistent no matter what wheel is moving
            double frontRightSpeed = leftY - leftX - rightX;        // left x depends on wheel, visualize it and it matches how strafing should be
              double backLeftSpeed = leftY - leftX + rightX;        // right x, One side positive one side negative, will make it turn
             double backRightSpeed = leftY + leftX - rightX;



             double max = Math.max(
                     Math.max(Math.abs(frontLeftSpeed), Math.abs(frontRightSpeed)),     // nested max statements, finds the largest abs value of all front speeds
                     Math.max(Math.abs(backLeftSpeed), Math.abs(backRightSpeed)));


             if(max > 1) {                                  // normalizes all values to make sure they are between -1 and 1, but still proportional to the original values.
                 frontLeftSpeed =  frontLeftSpeed / max;    // so if the max value is 3, that value then becomes 1 and all other values shrink accordingly.
                 frontRightSpeed = frontRightSpeed / max;
                 backLeftSpeed =   backLeftSpeed / max;
                 backRightSpeed =  backRightSpeed / max;

             }

             frontLeft.set(frontLeftSpeed);
             frontRight.set(frontRightSpeed);
             backLeft.set(backLeftSpeed);
             backRight.set(backRightSpeed);

             telemetry.addData("front left",frontLeft);
             telemetry.addData("front right",frontRight);
             telemetry.addData("back left",backLeft);
             telemetry.addData("back right",backRight);
             telemetry.update();
        }
    }
}
