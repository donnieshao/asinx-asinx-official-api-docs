<?php

// PHP online runtime: https://www.nhooo.com/tool/php/
class Signature
{
    public static function main()
    {
        $apiKey = "b635dd5c87f7bf73387929203321b1e1";
        $data = '/user/register{"email":"188888888662@188.com","mobileNumber":"18888888867","mobilePrefix":"1"}';

        echo "origin String:" . $data . PHP_EOL;

        $data = self::encrypt($data, $apiKey);
        echo "base64&aes128 String:" . $data . PHP_EOL;
        
        $sign = md5($data);
        echo "sign:" . $sign . PHP_EOL;

        $data = self::decrypt($data, $apiKey);
        echo "origin String:" . $data . PHP_EOL;
    }

    // encrypt
    // Encrypt the given data and return the encrypted hexadecimal string
    public static function encrypt($data, $apiKey)
    {
        $dataBytes = base64_encode($data);
        $apiKeyBytes = self::hexToByteArray($apiKey);
        $cipher = "AES-128-ECB";
        $encrypted = openssl_encrypt($dataBytes, $cipher, $apiKeyBytes, OPENSSL_RAW_DATA);
        return bin2hex($encrypted);
    }

    // decrypt 
    // Decrypt data encrypted by the encrypt function and return the decrypted string
    public static function decrypt($data, $apiKey)
    {
        $dataBytes = self::hexToByteArray($data);
        $raw = self::hexToByteArray($apiKey);
        $cipher = "AES-128-ECB";
        $original = openssl_decrypt($dataBytes, $cipher, $raw, OPENSSL_RAW_DATA);
        $original = base64_decode($original);
        return $original;
    }

    // hexToByteArray 
    // Convert hexadecimal string to byte array
    public static function hexToByteArray($inHex)
    {
        $hexlen = strlen($inHex);
        if ($hexlen % 2 == 1) {
            $hexlen++;
            $inHex = "0" . $inHex;
        }
        return pack("H*", $inHex);
    }

    // bytesToHex 
    // Convert byte array to hexadecimal string
    public static function bytesToHex($bytes)
    {
        return bin2hex($bytes);
    }

    // hexToByte 
    // Convert hexadecimal string to byte
    public static function hexToByte($inHex)
    {
        return hexdec($inHex);
    }
}

Signature::main();