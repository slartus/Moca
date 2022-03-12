<?php

$action = $_GET["action"] ?? die();

$password = $_GET["password"] ?? die();
$legalPasswords = array(
    ""
);

if (!in_array($password, $legalPasswords)) die();

include_once "simple_html_dom.php";


const PIRATBIT = "piratbit";
const KINOZAL = "kinozal";

const TYPE_MOVIE = "movie";
const TYPE_SERIES = "series";
const TYPE_ANIMATION = "animation";
const TYPE_ANIMATION_SERIES = "animation_series";

class Piratbit
{
    private $site_data;
    private const ENDPOINT = "http://lazy.piratbit.fun";
    private const LOGIN = "";
    private const PASS = "";

    private function login()
    {
        $curl = curl_init();

        $endPoint = self::ENDPOINT;
        curl_setopt_array($curl, array(
            CURLOPT_URL => "$endPoint/login.php",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HEADER => true,
            CURLOPT_ENCODING => '',
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 0,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => 'POST',
            CURLOPT_POSTFIELDS => array('login_password' => self::PASS, 'login_username' => self::LOGIN, 'login' => 'Вход'),
            CURLOPT_HTTPHEADER => array(
                'Cookie: bb_extra=r16bi76ropbbei86r5tosmmjj2; b=b'
            ),
        ));

        $response = curl_exec($curl);
        preg_match_all('/^Set-Cookie:\s*([^\r\n]*)/mi', $response, $ms);

        $site_data = null;
        foreach ($ms[1] as $m) {
            list($name, $value) = explode('=', $m, 2);
            if ($name == "site_data")
                $site_data = $value;
        }

        curl_close($curl);
        $this->site_data = $site_data;
    }

    function search($query)
    {
        $this->login();

        $endPoint = self::ENDPOINT;
        $url = "$endPoint/tracker/?ss=$query";

        $response = $this->get($url);

        $html = str_get_html($response);
        $trs = $html->find("tr.hl-tr");

        $torrents = array();
        foreach ($trs as $tr) {

            $tds = $tr->find("td");

            $titleTd = $tds[2];
            $titleA = $titleTd->find(".title", 0);
            $id = preg_split("/\//", $titleA->href)[2];
            $title = html_entity_decode(trim($titleA->plaintext));

            $sizeTd = $tds[3];
            $size = html_entity_decode(trim($sizeTd->find("div", 0)->plaintext));

            $seedsTd = $tds[4];
            $seeds = html_entity_decode(trim($seedsTd->plaintext));

            $peersTd = $tds[5];
            $peers = html_entity_decode(trim($peersTd->plaintext));

            $downloadsTd = $tds[6];
            $downloads = html_entity_decode(trim($downloadsTd->plaintext));

            $dateTd = $tds[7];
            $date = date('d.m.Y H:i:s', html_entity_decode(trim($dateTd->find("u", 0)->plaintext)));

            $torrentUrl = "/torrents.php?action=torrent&provider=piratbit&id=$id";

            $torrent = array(
                "title" => $title,
                "size" => $size,
                "seeds" => $seeds,
                "peers" => $peers,
                "downloads" => $downloads,
                "date" => $date,
                "torrent_url" => $torrentUrl
            );
            $torrents[] = $torrent;
        }
        echo json_encode($torrents);
    }

    function torrent($id)
    {
        $this->login();
        $endPoint = self::ENDPOINT;
        $response = $this->get("$endPoint/topic/$id");

        $html = str_get_html($response);
        $a = $html->find("a[class=btn btn-sm btn-success]", 0);
        $href = $a->href;

        $response = $this->get("$endPoint$href");

        header('Content-Disposition: attachment; filename="piratbit.' . $id . '.torrent"');
        header('Content-Type: application/x-bittorrent;  name="piratbit.' . $id . '.torrent"');
        echo $response;
    }

    private function get($url)
    {
        $curl = curl_init();
        $site_data = $this->site_data;
        curl_setopt_array($curl, array(
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => '',
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 0,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => 'GET',
            CURLOPT_HTTPHEADER => array(
                "Cookie: site_data=$site_data"
            ),
        ));

        $response = curl_exec($curl);

        curl_close($curl);
        return $response;
    }
}

class Kinozal
{
    private const ENDPOINT = "http://kinozal.tv";
    private const LOGIN = "";
    private const PASS = "";

    private $cookies = array();

    private function getCookieHeader(): string
    {
        $cookieHeader = "Cookie: ";
        foreach ($this->cookies as $key => $key_value) {
            $cookieHeader .= $key . '=' . $key_value . ";";
        }
        return $cookieHeader;
    }

    private function login()
    {
        $endPoint = self::ENDPOINT;

        $curl = curl_init();

        curl_setopt_array($curl, array(
            CURLOPT_URL => "$endPoint/takelogin.php",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HEADER => true,
            CURLOPT_ENCODING => '',
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 0,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => 'POST',
            CURLOPT_POSTFIELDS => array('password' => self::PASS, 'username' => self::LOGIN, 'returnto' => '')
        ));

        $response = curl_exec($curl);
        preg_match_all('/^Set-Cookie:\s*([^;]*)/mi', $response, $ms);
        $cookies = array();
        foreach ($ms[1] as $m) {
            parse_str($m, $cookie);
            $cookies = array_merge($cookies, $cookie);
            $this->cookies = $cookies;
        }

        curl_close($curl);
    }

    function search($query, $type)
    {
        $this->login();

        $endPoint = self::ENDPOINT;

        $cat = 0;
        switch ($type) {
            case TYPE_MOVIE:
                $cat = 1002;
                break;
            case TYPE_SERIES:
                $cat = 1001;
                break;
            case TYPE_ANIMATION_SERIES:
            case TYPE_ANIMATION:
                $cat = 1003;
                break;
        }

        $page = $this->get("$endPoint/browse.php?s=$query&c=$cat");
        $html = str_get_html($page);
        $trs = $html->find("tr.bg");
        $torrents = array();
        foreach ($trs as $tr) {
            $titleTd = $tr->find("td.nam", 0);
            $title = $titleTd->plaintext;
            $url = $titleTd->find("a", 0)->href;
            $id = preg_match('/id=(\d+)/', $url, $match) ? $match[1] : null;

            $tds = $tr->find("td");

            $sizeTd = $tds[3];
            $size = $sizeTd->plaintext;

            $seedsTd = $tds[4];
            $seeds = $seedsTd->plaintext;

            $peersTd = $tds[5];
            $peers = $peersTd->plaintext;

            $dateTd = $tds[6];
            $date = $dateTd->plaintext;

            $torrentUrl = "/torrents.php?action=torrent&provider=kinozal&id=$id";
            $torrent = array(
                "title" => $title,
                "size" => $size,
                "seeds" => $seeds,
                "peers" => $peers,
                "downloads" => null,
                "date" => $date,
                "torrent_url" => $torrentUrl
            );
            $torrents[] = $torrent;
        }
        echo json_encode($torrents);
    }

    function torrent($id)
    {
        $this->login();
        $response = $this->get("http://dl.kinozal.tv/download.php?id=$id");
        header('Content-Disposition: attachment; filename="kinozal.' . $id . '.torrent"');
        header('Content-Type: application/x-bittorrent;  name="kinozal.' . $id . '.torrent"');
        echo $response;
    }

    private function get($url)
    {
        $curl = curl_init();

        curl_setopt_array($curl, array(
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => '',
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 0,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => 'GET',
            CURLOPT_HTTPHEADER => array(
                $this->getCookieHeader()
            ),
        ));

        $response = curl_exec($curl);


        curl_close($curl);
        return $response;
    }
}

switch ($action) {
    case "search":
        header('Content-Type: application/json');
        //  header('Content-Type: text/html; charset=windows-1251');
        $provider = $_GET["provider"] ?? die("provider is null");
        $query = $_GET["query"] ?? null;
        $type = $_GET["type"] ?? null;
        switch ($provider) {
            case PIRATBIT:
                $client = new Piratbit();
                $client->search($query);
                break;
            case KINOZAL:
                $client = new Kinozal();
                $client->search($query, $type);
                break;
        }
        break;
    case "torrent":
        //header('Content-Type: text/html; charset=windows-1251');
        $provider = $_GET["provider"] ?? die("provider is null");
        $id = $_GET["id"];
        switch ($provider) {
            case PIRATBIT:
                $client = new Piratbit();
                $client->torrent($id);
                break;
            case KINOZAL:
                $client = new Kinozal();
                $client->torrent($id);
                break;
        }
        break;
}